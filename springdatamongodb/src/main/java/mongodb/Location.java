package mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Location {

	@Id private String id;
	
	@Indexed(name="2d")
	private double[] pos;
	
	private String name;

	public Location() {}
	
	public Location(String id, double x, double y, String name) {
		this.id = id;
		this.pos = new double[] {x,y};
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double[] getPos() {
		return pos;
	}

	public void setPos(double[] pos) {
		this.pos = pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
