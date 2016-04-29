package praestoo.Model;

import java.sql.*;

public class User {
	private int id;
	private int idAdr;
	private String gender;
	private String fName;
	private String lName;
	private String email;
	private String mobilePhone;
	private String homePhone;
	private String password;
	private float radius;
	private Date dateSignIn;
	
	// Queries
	
	final private static String CREATE = "INSERT INTO USER (idAdr, gender, fName, lName, email, mobilePhone, homePhone, password, radius, dateSignin) VALUES (?,?,?,?,?,?,?,?,?,?);";
	final private static String READ = "SELECT id, idAdr, gender, fName, lName, email, mobilePhone, homePhone, password, radius, dateSignin FROM USER WHERE id = ?;";
	final private static String UPDATE = "UPDATE USER SET idAdr = ?, gender = ?, fName = ?, lName = ?, email = ?, mobilePhone = ?, homePhone = ?, password = ?, radius = ? WHERE id = ?;";
	final private static String DELETE = "DELETE FROM USER WHERE id = ?;";
	
	// a new User signed in
	public User(int idAdr, String gender, String fName, String lName, String email,
			String mobilePhone, String homePhone, String password, float radius) {
		this.id = -1;
		this.idAdr = idAdr;
		this.gender = gender;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.homePhone = homePhone;
		this.password = password;
		this.radius = radius;
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    this.dateSignIn = sqlDate;
	}
	
	// an existing user
	public User(int id, int idAdr, String gender, String fName, String lName, String email,
			String mobilePhone, String homePhone, String password, float radius, Date dateSignIn) {
		this.id = id;
		this.idAdr = idAdr;
		this.gender = gender;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.mobilePhone = mobilePhone;
		this.homePhone = homePhone;
		this.password = password;
		this.radius = radius;
		this.dateSignIn = dateSignIn;
	}

	/**
	 * Insert or Update a User in the database
	 * @param pUser : the User to save
	 */
	public static void save(User pUser){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		if(pUser.getId() == -1){
			// Create a new user
			try{
				// get the database connection
				conn = Database.getInstance().getConnection();
				// prepare the query
				preparedStatement = conn.prepareStatement(CREATE);
				// Add the parameters
				preparedStatement.setInt(1, pUser.getIdAdr());
				preparedStatement.setString(2, pUser.getGender());
				preparedStatement.setString(3, pUser.getfName());
				preparedStatement.setString(4, pUser.getlName());
				preparedStatement.setString(5, pUser.getEmail());
				preparedStatement.setString(6, pUser.getMobilePhone());
				preparedStatement.setString(7, pUser.getHomePhone());
				preparedStatement.setString(8, pUser.getPassword());
				preparedStatement.setFloat(9, pUser.getRadius());
				preparedStatement.setDate(10, pUser.getDateSignIn());
				// Execute the query
				preparedStatement.executeUpdate();
				
			} catch (SQLException ex){				
				Database.printSQLException(ex);				
			} finally {				
				Database.closeQuietly(conn);
				Database.closeQuietly(preparedStatement);
			}
		} else {
			// Update an existing user
			try{
				// get the database connection
				conn = Database.getInstance().getConnection();
				// prepare the query
				preparedStatement = conn.prepareStatement(UPDATE);
				// Add the parameters
				preparedStatement.setInt(1, pUser.getIdAdr());
				preparedStatement.setString(2, pUser.getGender());
				preparedStatement.setString(3, pUser.getfName());
				preparedStatement.setString(4, pUser.getlName());
				preparedStatement.setString(5, pUser.getEmail());
				preparedStatement.setString(6, pUser.getMobilePhone());
				preparedStatement.setString(7, pUser.getHomePhone());
				preparedStatement.setString(8, pUser.getPassword());
				preparedStatement.setFloat(9, pUser.getRadius());
				preparedStatement.setInt(10, pUser.getId());
				// Execute the query
				preparedStatement.executeUpdate();
			} catch (SQLException ex){				
				Database.printSQLException(ex);				
			} finally {
				// Close everything
				Database.closeQuietly(preparedStatement);
				Database.closeQuietly(conn);				
			}
		}
	}
	
	/**
	 * Try to get a User from the database
	 * @param pId : the id of the user
	 * @return : User or NULL
	 */
	public static User read(int pId){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		User user = null;
		
		try{
			// get the database connection
			conn = Database.getInstance().getConnection();
			// prepare the query
			preparedStatement = conn.prepareStatement(READ);
			// add the parameters
			preparedStatement.setInt(1, pId);
			// Execute the query and get the result
			res = preparedStatement.executeQuery();
			// process the result
			if(res.next()){
				// if there is a result
				// get the data
				int id = res.getInt(1);
				int idAdr = res.getInt(2);
				String gender = res.getString(3);
				String fName = res.getString(4);
				String lName = res.getString(5);
				String email = res.getString(6);
				String mobilePhone = res.getString(7);
				String homePhone = res.getString(8);
				String password = res.getString(9);
				float radius = res.getFloat(10);
				Date dateSignIn = res.getDate(11);
				// create the User object
				user = new User(id, idAdr, gender, fName, lName, email, mobilePhone, homePhone, password, radius, dateSignIn);				
			}					
			
		} catch (SQLException ex){				
			Database.printSQLException(ex);				
		} finally {
			// Close everything
			Database.closeQuietly(res);
			Database.closeQuietly(preparedStatement);
			Database.closeQuietly(conn);
		}
		
		// return the result
		return user;
		
	}
	
	/**
	 * Delete a User from the database. TO BE USED CAREFULLY
	 * @param pId : the id of the user
	 */
	public static void delete(int pId){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try{
			// get the database connection
			conn = Database.getInstance().getConnection();
			// prepare the query
			preparedStatement = conn.prepareStatement(DELETE);
			// Add the parameters
			preparedStatement.setInt(1, pId);
			// Execute the query
			preparedStatement.executeUpdate();
			
		} catch (SQLException ex){				
			Database.printSQLException(ex);				
		} finally {				
			Database.closeQuietly(conn);
			Database.closeQuietly(preparedStatement);
		}
	}
	
	/** Getter and setter */
	
	public int getId() {
		return id;
	}

	public int getIdAdr() {
		return idAdr;
	}

	public void setIdAdr(int idAdr) {
		this.idAdr = idAdr;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Date getDateSignIn() {
		return dateSignIn;
	}
	
}

