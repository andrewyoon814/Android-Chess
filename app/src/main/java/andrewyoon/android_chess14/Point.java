package andrewyoon.android_chess14;
/**
 * Point class used for parameter passing
 * 
 * @author kevinbundschuh
 *
 */
public class Point {
	private int col;
	private int row;
	
	/**
	 * Point default constructor
	 * 
	 * @author kevinbundschuh
	 */
	public Point() {
		this.col=0;
		this.row=0;
	}
	
	/**
	 * point constructor with row and col params
	 * 
	 * @param row- point's row
	 * @param col- point's col
	 * 
	 * @author kevinbundschuh
	 */
	public Point(int row, int col){
		this.col = col;
		this.setRow(row);
	}
	
	/**
	 * point constructor with another point as the param
	 * 
	 * @param point - other Point object
	 */
	public Point(Point point){
		this.setCol(point.getCol());
		this.setRow(point.getRow());
	}
	
	/**
	 * get row for point
	 * 
	 * @return row 
	 * @author kevinbundschuh
	 */
	public int getRow() {
		return row;
	}

	/**
	 * set row for point
	 * 
	 * @param row
	 * @author kevinbundschuh
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * get col for point
	 * 
	 * @return col
	 * @author kevinbundschuh
	 */
	public int getCol() {
		return col;
	}

	/**
	 * set col for point
	 * 
	 * @param col
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * toString override for point
	 * 
	 * @author kevinbundschuh
	 * @return String to print
	 */
	public String toString(){
		return "["+this.row+","+this.col+"]";
	}
}
