package modest.coreUtility;

import java.util.Random;

/***** Initializing the Variables ****/
public class garbage extends BaseTest {

	static Random random = new Random();

	/*
	 * public ObjectRepository initializeGlobal(String UDID) throws Exception {
	 * 
	 * 
	 * 
	 *//***************************
		 * FUNCTION DEBUGGING ZONE
		 ***********************************************/
	/*
	
	*//**
		 * Generate a clean report with history of runs
		 * 
		 * 
		 * @param Project_path /**
		 * @function - generate_report => Generate a clean report with/without history
		 *           of runs
		 * @throws IOException
		 * @throws InterruptedException
		 */
	/*
	 * 
	 * public void generate_report() throws IOException, InterruptedException {
	 * 
	 * 
	 * String Project_path = System.getProperty("user.dir"); Process p; String
	 * command1 = "cd "+Project_path; String command3 = "allure generate --clean";
	 * 
	 * String allure_results = Project_path+"/allure-results/history"; String
	 * allure_reports = Project_path+"/allure-report/history";
	 * 
	 * // GoTo the results folder and copy history file from previous reports to
	 * results p = Runtime.getRuntime().exec(command1); p.waitFor(); try {
	 * 
	 * String source = allure_reports; File srcDir = new File(source); String
	 * destination = allure_results; File destDir = new File(destination);
	 * 
	 * FileUtils.copyDirectory(srcDir, destDir); } catch (Exception e) {System.out.
	 * println("This is either a fresh run or the run history has been deleted/moved. "
	 * );}
	 * 
	 * // Generate new Reports based on current Run
	 * 
	 * p = Runtime.getRuntime().exec(command3); p.waitFor();
	 * 
	 * // Serve the reports if the user wishes to see it at the test execution end
	 * automatically. if (Configuration_file.ServeReport) {
	 * 
	 * String auto_open_reports = Project_path+"/allure-report/index.html";
	 * System.out.println("Opening the run reports ");
	 * 
	 * File htmlFile = new File(auto_open_reports);
	 * Desktop.getDesktop().browse(htmlFile.toURI());
	 * 
	 * System.out.println("Reports generated !"); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *//**
		 * 
		 * 
		 * 
		 * @param Project_path
		 * @throws IOException
		 * @throws InterruptedException
		 */
	/*
	 * public void serve_report(boolean ServeReport) throws IOException,
	 * InterruptedException { Process p; if (ServeReport) {
	 * 
	 * String Project_path = System.getProperty("user.dir").toString();
	 * 
	 * // GoTo the results folder and copy history file from previous reports to
	 * results try { Project_path.replaceAll("/", "//"); } catch (Exception e ) {
	 * Project_path.replaceAll("\\", "\\\\"); }
	 * 
	 * String command1 = "cd "+Project_path; String command3 = "allure serve";
	 * 
	 * 
	 * p=Runtime.getRuntime().exec(command1); p=Runtime.getRuntime().exec(command3);
	 * 
	 * 
	 * }else { System.out.println("Report generated @ location "+System.getProperty(
	 * "user.dir"));
	 * 
	 * } try{ Thread.sleep(10000); p.destroy(); } catch (Exception e) {}
	 * 
	 * }
	 * 
	 * 
	 *//**
		 * @function - serve_report => Used to open the generated reports after test
		 *           suite execution finishes.
		 * @throws IOException
		 * @throws InterruptedException
		 *//*
			 * @SuppressWarnings("null") public void serve_report(boolean ServeReport)
			 * throws IOException, InterruptedException { Process p = null; if (ServeReport)
			 * {
			 * 
			 * String Project_path = System.getProperty("user.dir").toString(); try {
			 * Project_path.replaceAll("/", "//"); } catch (Exception e) {
			 * Project_path.replaceAll("\\", "\\\\"); }
			 * 
			 * String command1 = "cd " + Project_path; String command3 = "allure serve";
			 * 
			 * p = Runtime.getRuntime().exec(command1); p =
			 * Runtime.getRuntime().exec(command3);
			 * 
			 * } else { System.out.println("Report generated @ location " +
			 * System.getProperty("user.dir"));
			 * 
			 * } try { Thread.sleep(10000); p.destroy(); } catch (Exception e) { }
			 */
}