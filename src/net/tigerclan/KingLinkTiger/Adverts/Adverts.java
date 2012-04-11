package net.tigerclan.KingLinkTiger.Adverts;
     
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

    public class Adverts extends JavaPlugin {
    	
    	public static Adverts plugin;
    	
    	
    	public final Logger logger = Logger.getLogger("Minecraft");
    	public final Logger log = logger;
    	
    	public void onDisable(){
    		this.logger.info("DeathBan is now disabled");
    	}
    	
        public void onEnable() {
        	this.getConfig().options().copyDefaults(true);
        	this.saveConfig();
        	final String mysqluser = getConfig().getString("mysqluser");
        	final String mysqlpassword = getConfig().getString("mysqlpassword");
        	
        	
    		this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
    			int i=0;
			    public void run() {
			    	
			    	
					try {
						Class.forName("com.mysql.jdbc.Driver");
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					
					Connection connection = null;
					
					try{
						connection = DriverManager.getConnection("jdbc:mysql://tigerclan.net:3306/TigerClan_Community_Minecraft_Factions", mysqluser, mysqlpassword);
					} catch (SQLException e) {
						log.info(e.getMessage());
						return;
					}
					
					
					Statement stmt = null;
					Statement stmt2 = null;
					ResultSet rs = null;
					ResultSet rs2 = null;

					try{

					    	int id = (i+1);
					    	stmt2 = connection.createStatement();
							rs2 = stmt2.executeQuery("SELECT * FROM TigerClan_Community_Minecraft_Factions.Adverts WHERE id="+id+"");
							log.info("SELECT * FROM TigerClan_Community_Minecraft_Factions.Adverts WHERE id="+id+"");
					    
							while(rs2.next()){
								String content = rs2.getString("content");							
								getServer().broadcastMessage(ChatColor.GOLD + "[ADVERT] " + content);
							}
							
							stmt = connection.createStatement();
							rs = stmt.executeQuery("SELECT * FROM TigerClan_Community_Minecraft_Factions.Adverts");
							rs.last();
							int num_adverts = rs.getRow();
							
							if(num_adverts == (i+1)){
								i=0;
							}else{
								i++;
							}
					} catch (SQLException e) {
						log.severe("EXCEPTION "+e.getMessage());
					}
					
					
			    }
			}, 0L, 1200L);//reset to 1200L when done = 1200/20=60 seconds Make this configurable ask for seconds in config and *20 to get this number
    		
    		
    		
    		
    		

        }
}