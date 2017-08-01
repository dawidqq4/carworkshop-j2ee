package applicaton.workshop.web.view.home;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class ThemeChoice implements Serializable {
	private static final long serialVersionUID = 391166927890589613L;
	
	public static final String[] POSSIBLE_THEMES = { "afterdark", "afternoon", "afterwork", "aristo", "black-tie",
			"blitzer", "bluesky", "casablanca", "cruze", "cupertino", "dark-hive", "dot-luv", "eggplant", "excite-bike",
			"flick", "glass-x", "home", "hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc", "overcast",
			"pepper-grinder", "redmond", "rocket", "sam", "smoothness", "south-street", "start", "sunny",
			"swanky-purse", "trontastic", "twitter bootstrap", "ui-darkness", "ui-lightness", "vader" };

	public String[] getThemes() {
		return (POSSIBLE_THEMES);
	}
}
