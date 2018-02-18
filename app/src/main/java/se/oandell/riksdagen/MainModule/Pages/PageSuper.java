package se.oandell.riksdagen.MainModule.Pages;


/**
 * Superclass for pages in the MainActivity
 */
public class PageSuper {
    private String name;
    private int symbol; //Page logo
    private int banner;
    private int pageNumber;

    public PageSuper(String name, int symbol, int banner){
        this.name = name;
        this.symbol = symbol;
        this.banner = banner;
        pageNumber = 1;
    }

    public int getBanner() {
        return banner;
    }

    public String getName(){
        return name;
    }

    public int getSymbol() {
        return symbol;
    }

    public int getPageNumber(){
        return pageNumber;
    }

    public void nextPage(){
        pageNumber++;
    }

    public void previousPage(){
        pageNumber--;
        if(pageNumber < 1){
            pageNumber = 1;
        }
    }

    public void resetPageNumber(){
        pageNumber = 1;
    }

}


