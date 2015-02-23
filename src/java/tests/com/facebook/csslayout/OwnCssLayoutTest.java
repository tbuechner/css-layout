package com.facebook.csslayout;

import org.junit.Test;

public class OwnCssLayoutTest {

    public static final float WIDTH = 1000;

    @Test
	public void layoutCalculation() throws Exception {
		// arrange
		final CSSNode slide = createSlide();

		// act
		slide.calculateLayout();

		// assert
        System.out.println(NodeToString.toString(slide));
	}

	private CSSNode createSlide() {
		final CSSNode node = new CSSNode("slide");
		node.setStyleWidth(WIDTH).setStyleHeight(750);
        node.setFlexDirection(CSSFlexDirection.COLUMN);
		node.setAlignItems(CSSAlign.STRETCH);
		node.appendChild(createSlideContent());
		return node;
	}

	private CSSNode createSlideContent() {
		final CSSNode node = new CSSNode("slidecontent");
		node.setFlexDirection(CSSFlexDirection.COLUMN);
		node.setFlex(1);
        node.setPadding(Spacing.ALL, 10);
        node.appendChild(createHeader());
		node.appendChild(createTimelineTop());
		node.appendChild(createActivities());
		node.appendChild(createTimelineBottom());
		node.appendChild(createFooter());

		return node;
	}

	private CSSNode createHeader() {
		final CSSNode node = new CSSNode("header");
		node.setFlexDirection(CSSFlexDirection.ROW);
        node.setStyleHeight(60);
		node.appendChild(createHeaderLeftOrRight("headerLeft"));
		node.appendChild(createHeaderCenter());
        node.appendChild(createHeaderLeftOrRight("headerRight"));
		return node;
	}

	private CSSNode createTimelineTop() {
		final CSSNode node = new CSSNode("timelineTop");
		node.setFlexDirection(CSSFlexDirection.ROW);
		node.setStyleHeight(75);
		node.appendChild(createTimelineTopLeft());
		node.appendChild(createTimelineTopCenter());
		node.appendChild(createTimelineTopRight());
		return node;
	}

	private CSSNode createTimelineBottom() {
		final CSSNode node = new CSSNode("timelineBottom");
		node.setFlexDirection(CSSFlexDirection.ROW);
        node.setStyleHeight(25);
		node.appendChild(createTimelineBottomLeft());
		node.appendChild(createTimelineBottomCenter());
		node.appendChild(createTimelineBottomRight());
		return node;
	}

	private CSSNode createActivities() {
		final CSSNode node = new CSSNode("activities");
		node.setFlexDirection(CSSFlexDirection.ROW);
		node.setFlex(1);
        node.appendChild(createCol1());
		node.appendChild(createCol2());
		node.appendChild(createCol3());
		return node;
	}

	private CSSNode createFooter() {
		final CSSNode node = new CSSNode("footer");
		node.setFlexDirection(CSSFlexDirection.ROW);
        node.setStyleHeight(60);
        node.appendChild(createHeaderLeftOrRight("footerLeft"));
		node.appendChild(createHeaderCenter());
        node.appendChild(createHeaderLeftOrRight("footerRight"));
		return node;
	}

	private CSSNode createHeaderLeftOrRight(String name) {
		final CSSNode node = new CSSNode(name);
        node.setStyleWidth(WIDTH * 0.15f);
		return node;
	}

	private CSSNode createHeaderCenter() {
		final CSSNode node = new CSSNode("headerCenter");
		node.setFlex(1);
		return node;
	}

	private CSSNode createTimelineTopLeft() {
		final CSSNode node = new CSSNode("timelineTopLeft");
        node.setStyleWidth(180);
        return node;
	}

	private CSSNode createTimelineTopCenter() {
		final CSSNode node = new CSSNode("timelineTopCenter");
		node.setFlexDirection(CSSFlexDirection.COLUMN);
		node.appendChild(createTimelineTop("timelineTop1"));
		node.appendChild(createTimelineTop("timelineTop2"));
		node.appendChild(createTimelineTopWithTimeEntities("timelineTop3", 20));
		return node;
	}

	private CSSNode createTimelineTopRight() {
		final CSSNode node = new CSSNode("timelineTopRight");
        node.setStyleWidth(120);
        return node;
	}

	private CSSNode createTimelineTop(String name) {
		final CSSNode node = new CSSNode(name);
        node.setFlex(1);
        return node;
	}

    private CSSNode createTimelineTopWithTimeEntities(String name, int numberOfEntities) {
        final CSSNode node = new CSSNode(name);
        node.setFlex(1);
        for (int i = 0; i < numberOfEntities; i++) {
            node.appendChild(createTimeEntity());
        }
        return node;
    }

    private CSSNode createTimeEntity() {
        final CSSNode node = new CSSNode("timeEntity");
        node.setFlex(1);
        return node;
    }

    private CSSNode createTimelineBottomLeft() {
		final CSSNode node = new CSSNode("timelineBottomLeft");
        node.setStyleWidth(120);
        return node;
	}

	private CSSNode createTimelineBottomCenter() {
		final CSSNode node = new CSSNode("timelineBottomCenter");
		node.setFlexDirection(CSSFlexDirection.COLUMN);
		node.appendChild(createTimelineBottom1());
		return node;
	}

	private CSSNode createTimelineBottomRight() {
		final CSSNode node = new CSSNode("timelineBottomRight");
        node.setStyleWidth(120);
        return node;
	}

	private CSSNode createTimelineBottom1() {
		final CSSNode node = new CSSNode("timelineBotom1");
        node.setFlex(1);
        return node;
	}

	private CSSNode createCol1() {
		final CSSNode node = new CSSNode("col1");
        node.setStyleWidth(180);
        return node;
	}

	private CSSNode createCol2() {
		final CSSNode node = new CSSNode("col2");
        node.setFlex(1);
        return node;
	}

	private CSSNode createCol3() {
		final CSSNode node = new CSSNode("col3");
        node.setStyleWidth(120);
		return node;
	}
}
