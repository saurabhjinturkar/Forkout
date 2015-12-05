/**
 * 
 */
package com.example.paypro.core;

/**
 * @author jintu
 *
 */
public class DBValues {
	
	public enum GroupMembers {
		    user_id,
		    group_id,
		    created_at,
		    updated_at;
	}
	
	public enum Groups {
		    name,
		    description,
		    group_admin_id,
		    created_at,
		    updated_at;
	}
	
	public enum Transactions {
		    description,
		    details,
		    amount,
		    user_id,
		    group_id,
		    group_member_id,
		    created_at,
		    updated_at;
	}
	
	public enum Users {
		    first_name,
		    last_name,
		    email,
		    contact_number,
		    registration_status,
		    created_at,
		    updated_at;
	}
	
	public enum Queue {
			id,
			type,
			object,
			status;
	}
}