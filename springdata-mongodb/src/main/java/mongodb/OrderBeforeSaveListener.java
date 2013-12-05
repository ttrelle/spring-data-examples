package mongodb;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

import com.mongodb.DBObject;

/**
 * Equivalent of a domain method annotated by <code>PrePersist</code>.
 * <p/>
 * This handler shows how to implement your custom UUID generation.
 * @author Tobias Trelle
 */
public class OrderBeforeSaveListener extends AbstractMongoEventListener<Order>  {

	@Override
	public void onBeforeSave(Order source, DBObject dbo) {
		if ( source.getId() == null ) {
			// TODO use a better UUID generator in production
			dbo.put("_id","" + Math.random() );
		}
		
		if ( source.getDate() == null ) {
			dbo.put("date", new Date());
		}
	}

}
