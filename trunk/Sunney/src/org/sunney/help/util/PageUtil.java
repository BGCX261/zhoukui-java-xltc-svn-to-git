package org.sunney.help.util;


/**
 * @author fisher
 * @description  分页查询工具类,构造分页查询对象
 * @date 2011-9-16
 */
public class PageUtil {
    
    
    /**
     * Use the origin page to create a new page
     * @param page
     * @param totalRecords
     * @return
     */
    public static Pagenation createPage(Pagenation page, int totalRecords){
        return createPage(page.getEveryPage(), page.getCurrentPage(), totalRecords);
    }
    
    /**  
     * the basic page utils not including exception handler
     * @param everyPage
     * @param currentPage
     * @param totalRecords
     * @return page
     */
    public static Pagenation createPage(int everyPage, int currentPage, int totalRecords){
        everyPage = getEveryPage(everyPage);
        currentPage = getCurrentPage(currentPage);
        int beginIndex = getBeginIndex(everyPage, currentPage);
        int totalPage = getTotalPage(everyPage, totalRecords);
        boolean hasNextPage = hasNextPage(currentPage, totalPage);
        boolean hasPrePage = hasPrePage(currentPage);
        
        return new Pagenation(hasPrePage, hasNextPage,  
            	 	    everyPage, totalPage, 
            	 	    currentPage, beginIndex);
    }
    
    private static int getEveryPage(int everyPage){
        return everyPage == 0 ? 10 : everyPage;
    }
    
    private static int getCurrentPage(int currentPage){
        return currentPage == 0 ? 1 : currentPage;
    }
    
    private static int getBeginIndex(int everyPage, int currentPage){
        return (currentPage - 1) * everyPage; 
    }
	
    private static int getTotalPage(int everyPage, int totalRecords){
        int totalPage = 0;
		
        if(totalRecords % everyPage == 0)
            totalPage = totalRecords / everyPage; 
        else
        	totalPage = totalRecords / everyPage + 1 ;
		
        return totalPage;
    }
    
    private static boolean hasPrePage(int currentPage){
        return currentPage == 1 ? false : true; 
    }
    
    private static boolean hasNextPage(int currentPage, int totalPage){
        return currentPage == totalPage || totalPage == 0 ? false : true;
    }
    

}
