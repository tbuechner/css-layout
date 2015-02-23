package com.facebook.csslayout;

import org.junit.Test;

public class OwnCssLayoutTest {

	@Test
	public void layoutCalculation() throws Exception {
		// arrange
		final CSSNode slide = createSlide();

		// act
		slide.calculateLayout();

		// assert
		System.out.println(slide.toString());
	}

	private CSSNode createSlide() {
		final CSSNode slide = new CSSNode("slide");
		slide.setStyleWidth(800).setStyleHeight(600);
		slide.setPadding(Spacing.ALL, 10);
		slide.setFlexDirection(CSSFlexDirection.COLUMN);
		slide.setAlignItems(CSSAlign.STRETCH);

		slide.appendChild(createSlideContent());
		return slide;
	}

	private CSSNode createSlideContent() {
		final CSSNode slideContent = new CSSNode("contentslide");
		slideContent.setFlexDirection(CSSFlexDirection.COLUMN);
		// flex: 1 0 0;
		slideContent.setFlex(1);

		slideContent.appendChild(createHeader());
		slideContent.appendChild(createTimelineTop());
		slideContent.appendChild(createActivities());
		slideContent.appendChild(createTimelineBottom());
		slideContent.appendChild(createFooter());

		return slideContent;
	}

	private CSSNode createHeader() {
		final CSSNode header = new CSSNode("header");
		header.setFlexDirection(CSSFlexDirection.ROW);
		// flex: 0 0 60px;
		// slide.setFlex(flex);

		header.appendChild(createHeaderLeft());
		header.appendChild(createHeaderCenter());
		header.appendChild(createHeaderRight());
		return header;
	}

	private CSSNode createTimelineTop() {
		final CSSNode header = new CSSNode("timelineTop");
		header.setFlexDirection(CSSFlexDirection.ROW);
		// flex: 0 0 75px;
		// slide.setFlex(flex);

		header.appendChild(createTimelineTopLeft());
		header.appendChild(createTimelineTopCenter());
		header.appendChild(createTimelineTopRight());
		return header;
	}

	private CSSNode createTimelineBottom() {
		final CSSNode header = new CSSNode("timelineBottom");
		header.setFlexDirection(CSSFlexDirection.ROW);
		// flex: 0 0 25px;
		// slide.setFlex(flex);

		header.appendChild(createTimelineBottomLeft());
		header.appendChild(createTimelineBottomCenter());
		header.appendChild(createTimelineBottomRight());
		return header;
	}

	private CSSNode createActivities() {
		final CSSNode activities = new CSSNode("activities");
		activities.setFlexDirection(CSSFlexDirection.ROW);
		// flex: 1 0 0;
		// slide.setFlex(flex);

		activities.appendChild(createCol1());
		activities.appendChild(createCol2());
		activities.appendChild(createCol3());
		return activities;
	}

	private CSSNode createFooter() {
		final CSSNode footer = new CSSNode("footer");
		footer.setFlexDirection(CSSFlexDirection.ROW);
		// flex: 0 0 20px;
		// footer.setFlex(flex);

		footer.appendChild(createFooterLeft());
		footer.appendChild(createFooterCenter());
		footer.appendChild(createFooterRight());
		return footer;
	}

	private CSSNode createHeaderLeft() {
		final CSSNode headerLeft = new CSSNode("headerleft");
		// XXX flex: 0 0 15%;
		headerLeft.setFlex(0.15f);
		return headerLeft;
	}

	private CSSNode createHeaderCenter() {
		final CSSNode headerCenter = new CSSNode("headercenter");
		// XXX flex: 1 0 0;
		headerCenter.setFlex(0f);
		return headerCenter;
	}

	private CSSNode createHeaderRight() {
		final CSSNode headerRight = new CSSNode("headerright");
		// XXX flex: 0 0 15%;
		headerRight.setFlex(0.15f);
		return headerRight;
	}

	private CSSNode createTimelineTopLeft() {
		final CSSNode timelineTopLeft = new CSSNode("timelineTopLeft");
		// XXX flex: 0 0 180px;
		// headerLeft.setFlex();
		return timelineTopLeft;
	}

	private CSSNode createTimelineTopCenter() {
		final CSSNode timelineTopCenter = new CSSNode("timelineTopCenter");
		timelineTopCenter.setFlexDirection(CSSFlexDirection.COLUMN);
		// XXX flex: 1 0 0;
		// headerLeft.setFlex();

		timelineTopCenter.appendChild(createTimelineTop1());
		timelineTopCenter.appendChild(createTimelineTop2());
		timelineTopCenter.appendChild(createTimelineTop3());
		return timelineTopCenter;
	}

	private CSSNode createTimelineTopRight() {
		final CSSNode timelineTopRight = new CSSNode("timelineTopRight");
		// XXX flex: 0 0 120px;
		// headerLeft.setFlex();
		return timelineTopRight;
	}

	private CSSNode createTimelineTop1() {
		final CSSNode timelineTop1 = new CSSNode("timelineTop1");
		// XXX flex: 1 0 0;
		// timelineTop1.setFlex();
		return timelineTop1;
	}

	private CSSNode createTimelineTop2() {
		final CSSNode timelineTop2 = new CSSNode("timelineTop2");
		// XXX flex: 1 0 0;
		// timelineTop2.setFlex();
		return timelineTop2;
	}

	private CSSNode createTimelineTop3() {
		final CSSNode timelineTop3 = new CSSNode("timelineTop3");
		// XXX flex: 1 0 0;
		// timelineTop3.setFlex();
		return timelineTop3;
	}

	private CSSNode createTimelineBottomLeft() {
		final CSSNode timelineBottomLeft = new CSSNode("timelineBottomLeft");
		// XXX flex: 0 0 180px;
		// headerLeft.setFlex();
		return timelineBottomLeft;
	}

	private CSSNode createTimelineBottomCenter() {
		final CSSNode timelineBottomCenter = new CSSNode("timelineBottomCenter");
		timelineBottomCenter.setFlexDirection(CSSFlexDirection.COLUMN);
		// XXX flex: 1 0 0;
		// headerLeft.setFlex();

		timelineBottomCenter.appendChild(createTimelineBottom1());
		return timelineBottomCenter;
	}

	private CSSNode createTimelineBottomRight() {
		final CSSNode timelineBottomRight = new CSSNode("timelineBottomRight");
		// XXX flex: 0 0 120px;
		// headerLeft.setFlex();
		return timelineBottomRight;
	}

	private CSSNode createTimelineBottom1() {
		final CSSNode timelineBotom1 = new CSSNode("timelineBotom1");
		// XXX flex: 1 0 0;
		// timelineTop1.setFlex();
		return timelineBotom1;
	}

	private CSSNode createFooterLeft() {
		final CSSNode footerLeft = new CSSNode("footerLeft");
		// XXX flex: 0 0 15%;
		footerLeft.setFlex(0.15f);
		return footerLeft;
	}

	private CSSNode createFooterCenter() {
		final CSSNode footerCenter = new CSSNode("footerCenter");
		// XXX flex: 1 0 0;
		footerCenter.setFlex(0f);
		return footerCenter;
	}

	private CSSNode createFooterRight() {
		final CSSNode footerRight = new CSSNode("footerRight");
		// XXX flex: 0 0 15%;
		footerRight.setFlex(0.15f);
		return footerRight;
	}

	private CSSNode createCol1() {
		final CSSNode col1 = new CSSNode("col1");
		// XXX flex: 0 0 180px;
		// col1.setFlex();
		return col1;
	}

	private CSSNode createCol2() {
		final CSSNode col2 = new CSSNode("col2");
		// XXX flex: 1 0 0;
		// col2.setFlex();
		return col2;
	}

	private CSSNode createCol3() {
		final CSSNode col3 = new CSSNode("col3");
		// XXX flex: 0 0 120px;
		// col3.setFlex();
		return col3;
	}
}
