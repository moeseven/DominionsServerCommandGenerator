package MoesGames.DominionsServerSetup;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import MoesGames.DominionsServerSetup.Nations.Nation;
import MoesGames.DominionsServerSetup.Nations.NationLibrary;
import MoesGames.DominionsServerSetup.Randomize.MyRandomNumberGenerator;

public class DomRandomizer extends JFrame {

	int provincesPerPlayer = 15;
	int aiPlayers = 10;
	

	int thrones1, thrones2, thrones3;
	int ascendPoints = 0;
	int requiredPoints = 0;

	double deviation = 0; //maximum deviation in percent
	int age = 2;
	static String[] ages = { "1", "2", "3" };
	String aiLevel = "";
	static String[] aiLevels = {"easyai", "normai", "diffai", "mightyai", "masterai", "impai"};
	
	static int MAidLow = 43;
	static int MAids = 35;

	static int EAidLow = 5;
	static int EAids = 35;

	static int LAidLow = 80;
	static int LAids = 28;

	JTextField tf_provincesPerPlayer = new JTextField();
	JLabel l_provinces = new JLabel("provinces");
	JTextField tf_aiPlayers = new JTextField();
	JLabel l_ais = new JLabel("#AI");
	JTextField tf_deviation = new JTextField();
	JLabel l_deviation = new JLabel("deviation");
	JLabel l_age = new JLabel("age");
	JLabel l_aiLevel = new JLabel("difficulty");
	
	JTextField tf_navigation = new JTextField();
	final JComboBox<String> box_age = new JComboBox<String>(ages);
	final JComboBox<String> box_aiLevel = new JComboBox<String>(aiLevels);
	
	JLabel l_nations = new JLabel("nations");

	NationLibrary library = new NationLibrary();
	
	
	public DomRandomizer() throws HeadlessException {
		super();
		setTitle("Dom6 Server Command Generator");
		setSize(1640, 300);
		setLocationRelativeTo(null);// setLocation(100, 150);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set look and feel
		setDefaultLookAndFeelDecorated(true);
		setUpLibrary();
		
		JLabel labelM = new JLabel("draw from how many numbers?");
		final JLabel randomNumber = new JLabel("this will hold the random number");
		final JTextField serverCommand = new JTextField("this will hold the command to run");

		final JTextField numbersToDrawFrom = new JTextField();
		JButton b = new JButton("ranomize");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String randomString = "not a valid number";
				try {
					int randomNumber = (int) (Math.random() * Integer.parseInt(numbersToDrawFrom.getText())) + 1;
					randomString = Integer.toString(randomNumber);
				} catch (Exception ex) {
					// TODO: handle exception
				}

				randomNumber.setText(randomString);
				revalidate();
				repaint();
			}
		});

		JButton generateDomServerCommandButton = new JButton("generate dominions server command");
		generateDomServerCommandButton.addActionListener(new ActionListener() {



			public void actionPerformed(ActionEvent e) {
				readInput();
				generate();
				StringBuilder sbNations = new StringBuilder();
				try {
					ArrayList<Integer> numbers = MyRandomNumberGenerator.getRandomNumbersWithOffset(aiPlayers,
							getNumberOfIds());
					StringBuilder command = new StringBuilder("screen ");
					command.append("./dom6.sh -TSn --nosteam --hours 1000");
					command.append(" --era ");
					command.append(age);
					sbNations.append(numbers.size());
					sbNations.append(" ai nations: ");
					for (int i = 0; i < numbers.size(); i++) {
						command.append(" --");
						command.append(aiLevel);
						command.append(" ");
						command.append(getNationList().get(numbers.get(i)).getId());
						sbNations.append(getNationList().get(numbers.get(i)).getShortName());
						if (i < numbers.size()-1) {
							sbNations.append(", ");
						}						
					}
					command.append(" --requiredap ");
					command.append(requiredPoints);
					command.append(buildThronesString());
					command.append(" --port 2001");
					command.append(" --randmap ");
					command.append(provincesPerPlayer);
					command.append(" --vwrap");
					serverCommand.setText(command.toString());
				} catch (Exception ex) {
					// TODO: handle exception
				}
				l_nations.setText(sbNations.toString());
				revalidate();
				repaint();
			}

			private String buildThronesString() {
				StringBuilder throneCommand = new StringBuilder(" --thrones ");
				throneCommand.append(thrones1);
				throneCommand.append(" ");
				throneCommand.append(thrones2);
				throneCommand.append(" ");
				throneCommand.append(thrones3);
				return throneCommand.toString();
			}

			private int getRequiredPoints() {
				return ascendPoints / 2 + 1;
			}

			private int getPoints() {
				int players = aiPlayers + 2;
				return (players - 1) * 2;
			}

			
			private ArrayList<Nation> getNationList() {
				switch (age) {
				case 1:
					return library.getEANations();
				case 2:
					return library.getMANations();
				default:
					return library.getLANations();
				}
			}
			
			private int getNumberOfIds() {
				return getNationList().size();
			}

			private void readInput() {
				try {
					aiLevel = (String) box_aiLevel.getSelectedItem();
					provincesPerPlayer = Integer.parseInt(tf_provincesPerPlayer.getText());
					age = Integer.parseInt((String) box_age.getSelectedItem());
					aiPlayers = Integer.parseInt(tf_aiPlayers.getText());
					deviation = Integer.parseInt(tf_deviation.getText()) / 100.0;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			private void generate() {
				provincesPerPlayer = deviate(provincesPerPlayer-5)+5; // at least 5 provinces per player
				aiPlayers = deviate(aiPlayers);
				age = deviateTriangle(age);
				ascendPoints = deviate(getPoints()-1)+1; // at least 1 point
				requiredPoints = getRequiredPoints();
				setThrones();
			}

			private int deviateTriangle(int number) {
				if (Math.random() < deviation) {
					if (Math.random() > .5) {
						number = (number) % 3 + 1;
					} else {
						number = (number + 1) % 3 + 1;
					}
				}
				return number;
			}

			private int deviate(int toBeDeviated) {
				if (Math.random() > .5) {
					toBeDeviated -= deviation * Math.random() * toBeDeviated;
				} else {
					toBeDeviated += deviation * Math.random() * toBeDeviated;
				}
				return toBeDeviated;
			}

			private void setThrones() {
				int aps = ascendPoints;
				thrones1 = 0;
				thrones2 = 0;
				thrones3 = 0;
				while (aps > 0) {
					int t = 2;
					if (aps < 3) {
						t = aps;
					} else {
						t = deviateTriangle(t);
					}
					switch (t) {
					case 1:
						thrones1++;
						aps--;
						break;
					case 3:
						thrones3++;
						aps -= 3;
						break;
					default:
						thrones2++;
						aps -= 2;
						break;
					}
				}
			}
		});

		// set locations
		labelM.setBounds(50, 20, 200, 30);
		numbersToDrawFrom.setBounds(50, 55, 100, 30);
		b.setBounds(160, 55, 90, 30);
		randomNumber.setBounds(260, 55, 400, 30);
		
		tf_navigation.setBounds(50, 123, 255, 30);tf_navigation.setText("cd ~/.steam/steamapps/common/Dominions6/");
		generateDomServerCommandButton.setBounds(50, 145, 255, 30);
		box_age.setBounds(310, 145, 50, 30);
		box_age.setSelectedIndex(1);
		l_age.setBounds(310, 123, 50, 30);
		tf_aiPlayers.setBounds(365, 145, 50, 30);
		tf_aiPlayers.setText("10");
		l_ais.setBounds(365, 123, 50, 30);
		tf_provincesPerPlayer.setBounds(420, 145, 70, 30);
		tf_provincesPerPlayer.setText("15");
		l_provinces.setBounds(420, 123, 70, 30);
		tf_deviation.setBounds(590, 145, 70, 30);
		tf_deviation.setText("25");
		l_deviation.setBounds(590, 123, 70, 30);
		box_aiLevel.setBounds(495, 145, 90, 30); l_aiLevel.setBounds(495, 123, 90, 30); box_aiLevel.setSelectedIndex(3);
		l_nations.setBounds(50, 223, 850, 30);
		l_nations.setText("list of the random nations");
		

		serverCommand.setBounds(50, 185, 1500, 30);

		// add elements to the frame
		// add button to the frame
		add(generateDomServerCommandButton);
		add(b);
		add(labelM);
		add(randomNumber);
		add(numbersToDrawFrom);
		add(serverCommand);
		add(box_age);
		add(l_age);
		add(tf_aiPlayers);
		add(l_ais);
		add(tf_provincesPerPlayer);
		add(l_provinces);
		add(tf_deviation);
		add(l_deviation);
		add(tf_navigation);
		add(l_aiLevel);
		add(box_aiLevel);
		add(l_nations);
		setLayout(null);
		setVisible(true);
	}


	private void setUpLibrary() {
		MyJsonReader.loadNationsFromJson("./resources/nations.json", library);
	}
}
