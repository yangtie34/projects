package cn.gilight.framework.page;

  
import java.util.List;  
@SuppressWarnings("rawtypes") 
public class Page {  
    private int pagesize=20;//每页显示个数  
    private int curpage;//当前页  
    private int pagecount;//总页数  
    private int sumcount;//总记录数
    private String sortColumn;
    private String sortOrder;
	private List result;
  
    public int getSumcount() {  
       return sumcount;  
    }  
    public void setSumcount(int sumcount) {  
        this.sumcount = sumcount;  
    }  
    public int getPagecount() {  
        return pagecount;  
    }  
    public void setPagecount(int pagecount) {  
        this.pagecount = pagecount;  
    }  
    public int getCurpage() {  
        return curpage;  
    }  
    public void setCurpage(int curpage) {  
        this.curpage = curpage;  
    }  
      
    public int getPagesize() {  
        return pagesize;  
   }  
    public void setPagesize(int pagesize) {  
        this.pagesize = pagesize;  
    }  
    public List getResult() {  
        return result;  
    }  
    public void setResult(List result) {  
        this.result = result;  
    }
	public String getSortColumn() {
		return sortColumn;
	}
	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}  
