/**
 * Copyright (c) 2014, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.facebook.csslayout;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;

/**
 * A CSS Node. It has a style object you can manipulate at {@link #style}. After calling {@link #calculateLayout()}, {@link #layout} will be
 * filled with the results of the layout.
 */
public class CSSNode {

	private static enum LayoutState {
		/**
		 * Some property of this node or its children has changes and the current values in {@link #layout} are not valid.
		 */
		DIRTY,

		/**
		 * This node has a new layout relative to the last time {@link #markLayoutSeen()} was called.
		 */
		HAS_NEW_LAYOUT,

		/**
		 * {@link #layout} is valid for the node's properties and this layout has been marked as having been seen.
		 */
		UP_TO_DATE,
	}

	// Only one copy kept around to keep from allocating a bunch of MeasureOutput objects
	// NOT THREAD SAFE! NOT RE-ENTRANT SAFE!
	// Holger: static field was removed, to get threadsafety
	private final MeasureOutput MEASURE_OUTPUT = new MeasureOutput();

	public static interface MeasureFunction {

		/**
		 * Should measure the given node and put the result in the given MeasureOutput.
		 *
		 * NB: measure is NOT guaranteed to be threadsafe/re-entrant safe!
		 */
		public void measure(CSSNode node, float width, MeasureOutput measureOutput);
	}

	private final float[] mMargin = Spacing.newFullSpacingArray();
	private final float[] mPadding = Spacing.newFullSpacingArray();
	private final float[] mBorder = Spacing.newFullSpacingArray();

	// VisibleForTesting
	/* package */final CSSStyle style = new CSSStyle();
	/* package */final CSSLayout layout = new CSSLayout();
	/* package */final CachedCSSLayout lastLayout = new CachedCSSLayout();

	// 4 is kinda arbitrary, but the default of 10 seems really high for an average View.
	private final ArrayList<CSSNode> mChildren = new ArrayList<CSSNode>(4);

	private @Nullable CSSNode mParent;
	private @Nullable MeasureFunction mMeasureFunction = null;
	private LayoutState mLayoutState = LayoutState.DIRTY;

	private String name;

	public CSSNode() {
		// nothing to do
	}

	public CSSNode(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getChildCount() {
		return mChildren.size();
	}

	public CSSNode getChildAt(final int i) {
		return mChildren.get(i);
	}

	public void appendChild(final CSSNode child) {
		addChildAt(child, mChildren.size());
	}

	public void addChildAt(final CSSNode child, final int i) {
		if (child.mParent != null) {
			throw new IllegalStateException("Child already has a parent, it must be removed first.");
		}

		mChildren.add(i, child);
		child.mParent = this;
		dirty();
	}

	public void removeChildAt(final int i) {
		mChildren.remove(i).mParent = null;
		dirty();
	}

	public @Nullable CSSNode getParent() {
		return mParent;
	}

	/**
	 * @return the index of the given child, or -1 if the child doesn't exist in this node.
	 */
	public int indexOf(final CSSNode child) {
		return mChildren.indexOf(child);
	}

	public void setMeasureFunction(final MeasureFunction measureFunction) {
		if (!valuesEqual(mMeasureFunction, measureFunction)) {
			mMeasureFunction = measureFunction;
			dirty();
		}
	}

	public boolean isMeasureDefined() {
		return mMeasureFunction != null;
	}

	/* package */MeasureOutput measure(final float width) {
		if (!isMeasureDefined()) {
			throw new RuntimeException("Measure function isn't defined!");
		}
		MEASURE_OUTPUT.height = CSSConstants.UNDEFINED;
		MEASURE_OUTPUT.width = CSSConstants.UNDEFINED;
		Assertions.assertNotNull(mMeasureFunction).measure(this, width, MEASURE_OUTPUT);
		return MEASURE_OUTPUT;
	}

	/**
	 * Performs the actual layout and saves the results in {@link #layout}
	 */
	public void calculateLayout() {
		layout.resetResult();
		LayoutEngine.layoutNode(this, CSSConstants.UNDEFINED);
	}

	/**
	 * See {@link LayoutState#DIRTY}.
	 */
	/* package */boolean isDirty() {
		return mLayoutState == LayoutState.DIRTY;
	}

	/**
	 * See {@link LayoutState#HAS_NEW_LAYOUT}.
	 */
	public boolean hasNewLayout() {
		return mLayoutState == LayoutState.HAS_NEW_LAYOUT;
	}

	protected void dirty() {
		if (mLayoutState == LayoutState.DIRTY) {
			return;
		} else if (mLayoutState == LayoutState.HAS_NEW_LAYOUT) {
			throw new IllegalStateException("Previous layout was ignored! markLayoutSeen() never called");
		}

		mLayoutState = LayoutState.DIRTY;

		if (mParent != null) {
			mParent.dirty();
		}
	}

	/* package */void markHasNewLayout() {
		mLayoutState = LayoutState.HAS_NEW_LAYOUT;
	}

	/**
	 * Tells the node that the current values in {@link #layout} have been seen. Subsequent calls to {@link #hasNewLayout()} will return
	 * false until this node is laid out with new parameters. You must call this each time the layout is generated if the node has a new
	 * layout.
	 */
	public void markLayoutSeen() {
		if (!hasNewLayout()) {
			throw new IllegalStateException("Expected node to have a new layout to be seen!");
		}

		mLayoutState = LayoutState.UP_TO_DATE;
	}

	private void toStringWithIndentation(final StringBuilder result, final int level) {
		// Spaces and tabs are dropped by IntelliJ logcat integration, so rely on __ instead.
		final StringBuilder indentation = new StringBuilder();
		for (int i = 0; i < level; ++i) {
			indentation.append("__");
		}

		if (name != null && name.length() > 0) {
			indentation.append(name);
			indentation.append(" ");
		}

		result.append(indentation.toString());
		result.append(layout.toString());

		if (getChildCount() == 0) {
			return;
		}

		result.append(", children: [\n");
		for (int i = 0; i < getChildCount(); i++) {
			getChildAt(i).toStringWithIndentation(result, level + 1);
			result.append("\n");
		}
		result.append(indentation + "]");
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		this.toStringWithIndentation(sb, 0);
		return sb.toString();
	}

	protected boolean valuesEqual(final float f1, final float f2) {
		return FloatUtil.floatsEqual(f1, f2);
	}

	protected <T> boolean valuesEqual(@Nullable final T o1, @Nullable final T o2) {
		if (o1 == null) {
			return o2 == null;
		}
		return o1.equals(o2);
	}

	public CSSNode setFlexDirection(final CSSFlexDirection flexDirection) {
		if (!valuesEqual(style.flexDirection, flexDirection)) {
			style.flexDirection = flexDirection;
			dirty();
		}
		return this;
	}

	public CSSNode setJustifyContent(final CSSJustify justifyContent) {
		if (!valuesEqual(style.justifyContent, justifyContent)) {
			style.justifyContent = justifyContent;
			dirty();
		}
		return this;
	}

	public CSSNode setAlignItems(final CSSAlign alignItems) {
		if (!valuesEqual(style.alignItems, alignItems)) {
			style.alignItems = alignItems;
			dirty();
		}
		return this;
	}

	public CSSNode setAlignSelf(final CSSAlign alignSelf) {
		if (!valuesEqual(style.alignSelf, alignSelf)) {
			style.alignSelf = alignSelf;
			dirty();
		}
		return this;
	}

	public CSSNode setPositionType(final CSSPositionType positionType) {
		if (!valuesEqual(style.positionType, positionType)) {
			style.positionType = positionType;
			dirty();
		}
		return this;
	}

	public CSSNode setWrap(final CSSWrap flexWrap) {
		if (!valuesEqual(style.flexWrap, flexWrap)) {
			style.flexWrap = flexWrap;
			dirty();
		}
		return this;
	}

	public CSSNode setFlex(final float flex) {
		if (!valuesEqual(style.flex, flex)) {
			style.flex = flex;
			dirty();
		}
		return this;
	}

	public CSSNode setMargin(final int spacingType, final float margin) {
		setSpacing(mMargin, style.margin, spacingType, margin);
		return this;
	}

	public CSSNode setPadding(final int spacingType, final float padding) {
		setSpacing(mPadding, style.padding, spacingType, padding);
		return this;
	}

	public CSSNode setBorder(final int spacingType, final float border) {
		setSpacing(mBorder, style.border, spacingType, border);
		return this;
	}

	protected CSSNode setSpacing(final float[] spacingDef, final float[] cssStyle, final int spacingType, final float spacing) {
		if (!valuesEqual(spacingDef[spacingType], spacing)) {
			Spacing.updateSpacing(spacingDef, cssStyle, spacingType, spacing, 0);
			dirty();
		}
		return this;
	}

	public CSSNode setPositionTop(final float positionTop) {
		if (!valuesEqual(style.positionTop, positionTop)) {
			style.positionTop = positionTop;
			dirty();
		}
		return this;
	}

	public CSSNode setPositionBottom(final float positionBottom) {
		if (!valuesEqual(style.positionBottom, positionBottom)) {
			style.positionBottom = positionBottom;
			dirty();
		}
		return this;
	}

	public CSSNode setPositionLeft(final float positionLeft) {
		if (!valuesEqual(style.positionLeft, positionLeft)) {
			style.positionLeft = positionLeft;
			dirty();
		}
		return this;
	}

	public CSSNode setPositionRight(final float positionRight) {
		if (!valuesEqual(style.positionRight, positionRight)) {
			style.positionRight = positionRight;
			dirty();
		}
		return this;
	}

	public CSSNode setStyleWidth(final float width) {
		if (!valuesEqual(style.width, width)) {
			style.width = width;
			dirty();
		}
		return this;
	}

	public CSSNode setStyleHeight(final float height) {
		if (!valuesEqual(style.height, height)) {
			style.height = height;
			dirty();
		}
		return this;
	}

	public float getLayoutX() {
		return layout.x;
	}

	public float getLayoutY() {
		return layout.y;
	}

	public float getLayoutWidth() {
		return layout.width;
	}

	public float getLayoutHeight() {
		return layout.height;
	}
}
