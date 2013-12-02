/**
 *
 * CardExpireAlert.java
 * ZhangMin.name - zhangmin@zhangmin.name
 * org.ciotc.middleware.threadedtimertask
 *
 */
package org.ciotc.middleware.threadedtimertask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ciotc.middleware.threadedtimertask.dao.UserTargetOrgnaizeDto;

/**
 * @author ZhangMin.name
 *
 */
public class CardExpireAlert extends AbstractAlert {
	private static final Log logger = LogFactory.getLog(CardExpireAlert.class);
	@Override
	public void runAlertJob(){
		logger.info("CardExpireAlert job started...");
		HashMap<String,Integer> targetToUsers = new HashMap<String,Integer>();
		List<UserTargetOrgnaizeDto> utos = staffAlertDAO.getUTOByLBSTraceTable();
		Iterator<UserTargetOrgnaizeDto> it = utos.iterator();
		while(it.hasNext()){
			UserTargetOrgnaizeDto uto = it.next();
			if(uto.getValidDate() != null){
				Timestamp validdate = uto.getValidDate();
				if(validdate.before(new Date())){
				targetToUsers.put(uto.getTargetID(), uto.getUserID());
				}
			}
		}
		staffAlertDAO.alarm(2, 4, targetToUsers);
	}

}