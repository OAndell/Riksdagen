package se.oandell.riksdagen.MainModule.Pages;

/**
 * Created by oscar on 2017-07-08.
 */

public class Page extends PageSuper {

    private String apiQuery;

    public Page(String name, int symbol, int banner, String apiQuery) {
        super(name, symbol, banner);
        this.apiQuery = apiQuery;
    }

    public String getAPIquery(){
        return apiQuery + super.getPageNumber();
    }
}
