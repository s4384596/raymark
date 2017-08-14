package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import bean.Junction;
import bean.Road;
import util.FileUtil;

public class Main {
	static String environment = "testcase/testcase-1/test-simple.txt";
	static String query = "testcase/testcase-1/query-simple.txt";
	static ArrayList<Road> roads = new ArrayList<Road>();
	static ArrayList<Junction> junctions = new ArrayList<Junction>();

	public static void main(String[] args) {
		init();
		// System.out.println(roads.toString());
		// System.out.println(junctions.toString());
		// for (Junction temp : junctions) {
		// System.out.println(temp.getName());
		// System.out.println(temp.getConnectionRoad());
		// }
	}

	public static void init() {
		// ArrayList<Road> roads = new ArrayList<Road>();
		// ArrayList<Junction> junctions = new ArrayList<Junction>();

		File fileEnvironment = FileUtil.openFile(environment);
		File fileQuery = FileUtil.openFile(query);

		BufferedReader readerEnvironment = FileUtil.getReader(fileEnvironment);
		BufferedReader readerQuery = FileUtil.getReader(fileQuery);
		try {
			String tempLine = null;
			while ((tempLine = readerEnvironment.readLine()) != null) {
				Road tempRoad = new Road();
				boolean startJuncitonFlag = true;
				boolean endJuncitonFlag = true;
				String[] lineEnv = tempLine.split(" ; ");
				tempRoad.setName(lineEnv[0]);
				tempRoad.setLength(Float.parseFloat(lineEnv[3]));
				tempRoad.setnLots(Float.parseFloat(lineEnv[4]));
				if (junctions != null) {
					for (int i = 0; i < junctions.size(); i++) {
						if (lineEnv[1].equals(junctions.get(i).getName())) {
							tempRoad.setStartJunction(junctions.get(i));
							startJuncitonFlag = false;
							continue;
						} else if (lineEnv[2].equals(junctions.get(i).getName())) {
							tempRoad.setEndJunction(junctions.get(i));
							endJuncitonFlag = false;
							continue;
						}
						if (!startJuncitonFlag && !endJuncitonFlag) {
							break;
						}
					}
				}

				if (startJuncitonFlag) {
					Junction tempJuction = new Junction();
					tempJuction.setName(lineEnv[1]);
					junctions.add(tempJuction);
					tempRoad.setStartJunction(tempJuction);
				}
				if (endJuncitonFlag) {
					Junction tempJuction = new Junction();
					tempJuction.setName(lineEnv[2]);
					junctions.add(tempJuction);
					tempRoad.setEndJunction(tempJuction);
				}
				tempRoad.getStartJunction().addConnection(tempRoad);
				tempRoad.getEndJunction().addConnection(tempRoad);
				roads.add(tempRoad);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			readerEnvironment.close();
			readerQuery.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
