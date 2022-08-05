package database;

import model.*;

import tabs.AnalystSalesPanel;
import utils.PrintablePreparedStatement;

import java.sql.*;
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
        login("ora_apang11 ", "a23413743");
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

            String query = "INSERT INTO Game VALUES (?, ?, ?, ?, ?)";
			PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
			ps.setInt(1, game.getgID());
            ps.setInt(2, game.getBtID());
            ps.setInt(3, game.getRtID());
            ps.setDate(4, game.getDay());
            ps.setInt(5, game.getaID());
            ps.executeUpdate();
			connection.commit();

            query = "INSERT INTO SeasonDates VALUES (?, ?)";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setDate(1, game.getDay());
            ps.setString(2, game.getSeason());
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

    public ArrayList<String> getGameCasts(int gID) {
        ArrayList<String> languages = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT language FROM CASTS WHERE gid = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, gID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                languages.add(rs.getString("language"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return languages;
    }

    public Integer getMaxKey(String key, String table) {
        Integer ans = 0;
        try {
            String query;
            PrintablePreparedStatement ps;
            query = "SELECT " + key + " FROM " + table + " ORDER BY " + key + " DESC FETCH FIRST 1 ROWS ONLY";
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans = rs.getInt(key);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return ans;
    }

    public ArrayList<Integer> getKeys(String key, String table) {
        ArrayList<Integer> ans = new ArrayList<>();
        try {
            String query;
            PrintablePreparedStatement ps;
            query = "SELECT " + key + " FROM " + table + " ORDER BY " + key;
            ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ans.add(rs.getInt(key));
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
                arena = new Arena(rs.getInt("aID"), rs.getString("name"), rs.getString("city"), rs.getInt("capacity"));
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

    public ArrayList<Roster> getRosters(int tID) {
        ArrayList<Roster> rosters = new ArrayList<>();
        try {
            String query = "SELECT * FROM Roster WHERE tID = ?";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, tID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Roster r = new Roster(rs.getInt("tID"), rs.getString("season"), rs.getInt("year"),
                        rs.getInt("wins"), rs.getInt("losses"));
                rosters.add(r);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
            throw new RuntimeException(e.getMessage());
        }
        return rosters;
    }

    public ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            String query = "SELECT * FROM team ORDER BY name";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Team team = new Team(rs.getInt("tID"), rs.getString("name"), rs.getString("owner"));
                teams.add(team);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
//            throw new RuntimeException(e.getMessage());
        }
        return teams;
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

    public void refundTicket(int ticketNum) {
        // TODO: make ticket available again and remove from viewer
    }

    public ArrayList<Player> getPlayers(Team team) {
        ArrayList<Player> players = new ArrayList<>();
//        try {
//            String query = "SELECT * FROM  WHERE "; // players part of current roster
//            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Player player = new Player(rs.getInt("tmID"), rs.getString("position"), rs.getString("alias"));
//                players.add(player);
//            }
//            rs.close();
//            ps.close();
//        } catch (SQLException e) {
//            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
//            rollbackConnection();
////            throw new RuntimeException(e.getMessage());
//        }
        return players;
    }

    public void addCasts(Integer gID, Integer cID, String lang) {
        try {
            String query = "INSERT INTO Casts VALUES (?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, cID);
            ps.setInt(2, gID);
            ps.setString(3, lang);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ArrayList<SalesStruct> getGameSales() {
        ArrayList<SalesStruct> gameSalesList = new ArrayList<>();
        try {
            String query = "SELECT Game.gID, day, Team1.name AS btName, Team2.name AS rtName, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                    "FROM Game " +
                    "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                    "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                    "INNER JOIN Team Team1 ON Game.btID = Team1.tID " +
                    "INNER JOIN Team Team2 ON Game.rtID = Team2.tID " +
                    "WHERE Ticket.vID IS NOT NULL " +
                    "GROUP BY Game.gID, day, Team1.name, Team2.name " +
                    "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.GameSalesStruct gameSales = new AnalystSalesPanel.GameSalesStruct(
                        rs.getString("rtName") + " vs. " + rs.getString("btName"),
                        rs.getDate("day"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                gameSalesList.add(gameSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }

        return gameSalesList;
    }

    public void addTicket(int ticketnum, int vID, int gID, int aId, int seatNum) {
        try {
            String query = "INSERT INTO Ticket VALUES (?, ?, ?, ?, ?)";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ps.setInt(1, ticketnum);
            if (vID >= 0) {
                ps.setInt(2, vID);
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setInt(3, gID);
            ps.setInt(4, aId);
            ps.setInt(5, seatNum);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
    }

    public ArrayList<SalesStruct> getTeamSales() {
        ArrayList<SalesStruct> teamSalesList = new ArrayList<>();
        try {
            String query =
                    "SELECT Team.name, COUNT(Game.gID) AS totalGames, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                            "FROM Team " +
                            "INNER JOIN Game ON Game.btID = Team.tID OR Game.rtID = Team.tID " +
                            "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                            "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                            "INNER JOIN Team Team1 ON Game.btID = Team1.tID " +
                            "INNER JOIN Team Team2 ON Game.rtID = Team2.tID " +
                            "WHERE Ticket.vID IS NOT NULL " +
                            "GROUP BY Team.name " +
                            "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.TeamSalesStruct teamSales = new AnalystSalesPanel.TeamSalesStruct(
                        rs.getString("name"),
                        rs.getInt("totalGames"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                teamSalesList.add(teamSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return teamSalesList;
    }

    public ArrayList<SalesStruct> getArenaSales() {
        ArrayList<SalesStruct> arenaSalesList = new ArrayList<>();
        try {
            String query =
                    "SELECT Arena.name, Arena.city, COUNT(ticketNum) AS totalViewers, SUM(price) AS totalSales " +
                            "FROM Arena " +
                            "INNER JOIN Game ON Game.aID = Arena.aID " +
                            "INNER JOIN Ticket ON Game.gID = Ticket.gID " +
                            "INNER JOIN Seat ON Ticket.aID = Seat.aID AND Ticket.seatNum = Seat.seatNum " +
                            "WHERE Ticket.vID IS NOT NULL " +
                            "GROUP BY Arena.name, Arena.city " +
                            "ORDER BY totalViewers DESC, totalSales DESC";
            PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AnalystSalesPanel.ArenaSalesStruct teamSales = new AnalystSalesPanel.ArenaSalesStruct(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getInt("totalViewers"),
                        rs.getInt("totalSales")
                );
                arenaSalesList.add(teamSales);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(EXCEPTION_TAG + " " + e.getMessage());
            rollbackConnection();
        }
        return arenaSalesList;
    }
}
