package database;

import model.Arena;
import model.Game;
import model.Player;
import model.Team;
import utils.PrintablePreparedStatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
        login("ora_dennis34", "a94349206");
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void insertGame(Game game) {
//        INSERT INTO Game VALUES (1, 1, 2, '10-OCT-22', 1)
		try {
            String query = "INSERT INTO SeasonDates VALUES (?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setDate(1, game.getDay());
            ps.setString(2, game.getSeason());
            ps.executeUpdate();
            connection.commit();

            query = "INSERT INTO Game VALUES (?, ?, ?, ?, ?)";
			ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, game.getgID());
            ps.setInt(2, game.getBtID());
            ps.setInt(3, game.getRtID());
            ps.setDate(4, game.getDay());
            ps.setInt(5, game.getaID());
            ps.executeUpdate();
			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}
    public void deleteGame(int gID) {
		try {
			String query = "DELETE FROM Game WHERE gID = ?";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, gID);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				System.out.println(WARNING_TAG + " Branch " + gID + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

    public ArrayList<Game> getGames(int i, String attr, String term) {
        ArrayList<Game> list = new ArrayList<>();
        try {
            String query;
            PrintablePreparedStatement ps;
            if (attr.equals("")) {
                query = "SELECT * FROM GAME ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setInt(1, i);
            } else if (attr.equals("team")) {
                query = "SELECT * FROM GAME WHERE rtID = ? OR btID = ? ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setInt(1, Integer.parseInt(term));
                ps.setInt(2, Integer.parseInt(term));
                ps.setInt(3, i);
            } else {
                query = "SELECT * FROM GAME WHERE " + attr + " = ? ORDER BY day DESC FETCH FIRST ? ROWS ONLY";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//                ps.setString(1, attr);
                ps.setInt(1, Integer.parseInt(term));
                ps.setInt(2, i);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                query = "SELECT season FROM SeasonDates WHERE day = ?";
                ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
                ps.setDate(1, rs.getDate("day"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                Game temp = new Game(rs.getInt("gID"), rs.getInt("rtID"), rs.getInt("btID"),
                rs.getDate("day"), rs.getInt("aID"), rs2.getString("season"), rs.getDate("day").getYear() + 1900);
                list.add(temp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return list;
    }

    public Integer getMaxKey(String key, String table) {
        Integer ans = null;
        try {
            String query;
            PrintablePreparedStatement ps;
            query = "SELECT " + key + " FROM " + table + " ORDER BY " + key + " DESC FETCH FIRST 1 ROWS ONLY";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans = rs.getInt("gID");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return ans;
    }

    public Arena getArena(int aID) {
        Arena arena = null;
        try {
            String query = "SELECT * FROM Arena WHERE aID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, aID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arena = new Arena(rs.getInt("aID"), rs.getString("name"), rs.getString("city"));
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return arena;
    }

    public Team getTeam(int tID) {
        Team team = null;
        try {
            String query = "SELECT * FROM Team WHERE tID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                team = new Team(rs.getInt("tID"), rs.getString("name"), rs.getString("owner"));
            }
            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return team;
    }

    public ArrayList<Player> getRosterPlayers(int tmID, String season, int year) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            String query = "SELECT Player.tmID, Player.position, Player.alias FROM partofroster INNER JOIN Player ON partofroster.tmID=Player.tmID WHERE partofroster.season = ? AND partofroster.tID = ? AND partofroster.year = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setString(1, season);
            ps.setInt(2, tmID);
            ps.setInt(3, year + 1900);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player temp = new Player(rs.getInt("tmID"), rs.getString("position"), rs.getString("alias"));
                players.add(temp);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return players;
    }



//	public void insertBranch(BranchModel model) {
//		try {
//			String query = "INSERT INTO branch VALUES (?,?,?,?,?)";
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ps.setInt(1, model.getId());
//			ps.setString(2, model.getName());
//			ps.setString(3, model.getAddress());
//			ps.setString(4, model.getCity());
//			if (model.getPhoneNumber() == 0) {
//				ps.setNull(5, java.sql.Types.INTEGER);
//			} else {
//				ps.setInt(5, model.getPhoneNumber());
//			}
//
//			ps.executeUpdate();
//			connection.commit();
//
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
//
//	public BranchModel[] getBranchInfo() {
//		ArrayList<BranchModel> result = new ArrayList<BranchModel>();
//
//		try {
//			String query = "SELECT * FROM branch";
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ResultSet rs = ps.executeQuery();
//
//			while(rs.next()) {
//				BranchModel model = new BranchModel(rs.getString("branch_addr"),
//						rs.getString("branch_city"),
//						rs.getInt("branch_id"),
//						rs.getString("branch_name"),
//						rs.getInt("branch_phone"));
//				result.add(model);
//			}
//
//			rs.close();
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//		}
//
//		return result.toArray(new BranchModel[result.size()]);
//	}
//
//	public void updateBranch(int id, String name) {
//		try {
//			String query = "UPDATE branch SET branch_name = ? WHERE branch_id = ?";
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ps.setString(1, name);
//			ps.setInt(2, id);
//
//			int rowCount = ps.executeUpdate();
//			if (rowCount == 0) {
//				System.out.println(WARNING_TAG + " Branch " + id + " does not exist!");
//			}
//
//			connection.commit();
//
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//			rollbackConnection();
//		}
//	}
//
	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}
//
	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


//
//	public void databaseSetup() {
//		dropBranchTableIfExists();
//
//		try {
//			String query = "CREATE TABLE branch (branch_id integer PRIMARY KEY, branch_name varchar2(20) not null, branch_addr varchar2(50), branch_city varchar2(20) not null, branch_phone integer)";
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ps.executeUpdate();
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//		}
//
//		BranchModel branch1 = new BranchModel("123 Charming Ave", "Vancouver", 1, "First Branch", 1234567);
//		insertBranch(branch1);
//
//		BranchModel branch2 = new BranchModel("123 Coco Ave", "Vancouver", 2, "Second Branch", 1234568);
//		insertBranch(branch2);
//	}
//
//	private void dropBranchTableIfExists() {
//		try {
//			String query = "select table_name from user_tables";
//			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//			ResultSet rs = ps.executeQuery();
//
//			while(rs.next()) {
//				if(rs.getString(1).toLowerCase().equals("branch")) {
//					ps.execute("DROP TABLE branch");
//					break;
//				}
//			}
//
//			rs.close();
//			ps.close();
//		} catch (SQLException e) {
//			System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//		}
//	}
}
