/**
 * 
 */
package edu.upenn.cis350;

/**
 * @author Alex
 *
 */
public class PointOfInterest {
	// int title;
	// int info;
	int[] images;
	int mapNumber;
	
	protected PointOfInterest(int[] inImages, int mapNum) {
		images = inImages;
		mapNumber = mapNum;
	}
	
	// 0
	public static PointOfInterest poi0() {
		int[] _images = {R.drawable.poi_1gabe_kirchheimer_from_above};
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.INDOORS_CODE);
		return poi;
	}
	
	// 1
	public static PointOfInterest poi1() {
		int[] _images = {R.drawable.poi_2clarence};
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.STUDIO_CODE);
		return poi;
	}
	
	// 2
	public static PointOfInterest poi2() {
		int[] _images = { R.drawable.poi_3four_armed_iz, 
						  R.drawable.poi_3four_armed_iz2, 
						  R.drawable.poi_3tin_man };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.STUDIO_CODE);
		return poi;
	}

	// 3
	public static PointOfInterest poi3() {
		int[] _images = { R.drawable.poi_4types_of_tile,
						  R.drawable.poi_4commercial_painted_tile,
						  R.drawable.poi_4doily_tile,
						  R.drawable.poi_4surbeck_tile };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.BASEMENT_CODE);
		return poi;
	}

	// 4
	public static PointOfInterest poi4() {
		int[] _images = { R.drawable.poi_5julia_zagar,
						  R.drawable.poi_5eyes_doily_courtyard,
						  R.drawable.poi_5eyes_gallery,
						  R.drawable.poi_5eyes_gallery2 };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 5
	public static PointOfInterest poi5() {
		int[] _images = { R.drawable.poi_6ferdinand_cheval_reference,
						  R.drawable.poi_6nek_chand_reference };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 6
	public static PointOfInterest poi6() {
		int[] _images = { R.drawable.poi_7huppa,
						  R.drawable.poi_7michael_morgan };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 7
	public static PointOfInterest poi7() {
		int[] _images = { R.drawable.poi_8zagar_mosaic_blob,
						  R.drawable.poi_8zagar_mosaic_process };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 8
	public static PointOfInterest poi8() {
		int[] _images = { R.drawable.poi_9jeremiah_in_a_dream };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 9
	public static PointOfInterest poi9() {
		int[] _images = { R.drawable.poi_10south_street_history,
						  R.drawable.poi_10south_street_history2 };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 10
	public static PointOfInterest poi10() {
		int[] _images = { R.drawable.poi_11stained_glass_window,
						  R.drawable.poi_11luna_parc2 };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 11
	public static PointOfInterest poi11() {
		int[] _images = { R.drawable.poi_12sept11,
						  R.drawable.poi_12sept112 };
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.EXTERIOR_CODE);
		return poi;
	}

	// 12
	public static PointOfInterest poi12() {
		int[] _images = { R.drawable.standard_pmg_logo_jpeg};
		PointOfInterest poi = new PointOfInterest(_images, MapActivity.INDOORS_CODE);
		return poi;
	}
	
	public static PointOfInterest getPOI(int index) {
		if (index == 0) {
			return poi0();
		} else if (index == 1) {
			return poi1();
		} else if (index == 2) {
			return poi2();
		} else if (index == 3) {
			return poi3();
		} else if (index == 4) {
			return poi4();
		} else if (index == 5) {
			return poi5();
		} else if (index == 6) {
			return poi6();
		} else if (index == 7) {
			return poi7();
		} else if (index == 8) {
			return poi8();
		} else if (index == 9) {
			return poi9();
		} else if (index == 10) {
			return poi10();
		} else if (index == 11) {
			return poi11();
		} else if (index == 12) {
			return poi12();
		} else {
			return null;
		}
	}
}
