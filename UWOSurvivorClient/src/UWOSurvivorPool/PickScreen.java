package UWOSurvivorPool;

/* Author: CS2212 Group 2
 * File Name: StandingsScreen.java
 * Date: 25/01/2012
 * Project: UWOSurvivorPool
 * Course: CS2212b
 * Description:
 * */

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.command.Command;
import net.rim.device.api.command.CommandHandler;
import net.rim.device.api.command.ReadOnlyCommandMetadata;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.table.RichList;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.toolbar.ToolbarButtonField;
import net.rim.device.api.ui.toolbar.ToolbarManager;
import net.rim.device.api.util.StringProvider;

public class PickScreen extends MainScreen implements FieldChangeListener {
	/* Variables */
	private LabelField labelTempName, labelTempTribe, labelTempStatus; // various
																		// labels.
	private int score, numberOfPlayers;
	private String name, tribe, voteType, status;
	private FontFamily ff1; // fonts.
	private Font font2; // fonts.
	private Bitmap picture;
	private ButtonField button1;

	public PickScreen(String voteType, String userData) {
		super();

		/* TESTING PURPOSES, REMOVE WHEN DATA PERSISTANCE IS ACTIVE */
		status = "Active";
		picture = Bitmap.getBitmapResource("Russell-hantzSmall.jpg");
		name = "TimmyTimmyTimmyTimmy JonesJonesJonesJones";
		tribe = "WakahWakahWakahWakahWakahWakah";
		score = 102;
		numberOfPlayers = 120;
		/*--------------------------------------------------------*/

		this.voteType = voteType;

		VerticalFieldManager vertFieldManager = new VerticalFieldManager(
				VerticalFieldManager.USE_ALL_WIDTH
						| VerticalFieldManager.VERTICAL_SCROLLBAR) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				graphics.setColor(Color.BLACK);
				graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
				super.paint(graphics);
			}
		};
		;

		/* build the tool bar */
		ToolbarManager manager = new ToolbarManager();
		setToolbar(manager);
		try {
			/* Logout button */
			ToolbarButtonField toolbutton1 = new ToolbarButtonField(null,
					new StringProvider("Log Out"));
			toolbutton1.setCommandContext(new Object() {
				public String toString() {
					return "toolbutton1";
				}
			});
			/* if pressed, go back to Splash */
			toolbutton1.setCommand(new Command(new CommandHandler() {
				public void execute(ReadOnlyCommandMetadata metadata,
						Object context) {
					UiApplication.getUiApplication().pushScreen(
							new SplashScreen());
				}
			}));
			/* Exit button */
			ToolbarButtonField toolbutton2 = new ToolbarButtonField(null,
					new StringProvider("Exit"));
			toolbutton2.setCommandContext(new Object() {
				public String toString() {
					return "toolbutton2";
				}
			});
			/* if pressed, exit the system */
			toolbutton2.setCommand(new Command(new CommandHandler() {
				public void execute(ReadOnlyCommandMetadata metadata,
						Object context) {
					System.exit(0);
				}
			}));

			/* add buttons to the tool bar */
			manager.add(toolbutton1);
			manager.add(toolbutton2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		/*
		 * FIX LIST FOR IMAGES AND OTHER MULTILINE CONTESTANT FORMATTING
		 * ----------------------
		 */
		RichList list = new RichList(vertFieldManager, true, 3, 0);

		try { // set up the smaller list font
			ff1 = FontFamily.forName("Verdana");
			font2 = ff1.getFont(Font.BOLD, 20);
		} catch (final ClassNotFoundException cnfe) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Dialog.alert("FontFamily.forName() threw "
							+ cnfe.toString());
				}
			});
		}

		/* build the list */

		/*
		 * FILL THE LIST WITH ALL THE PLAYERS --------------------------
		 * 
		 * SHOW NAME, TRIBE, GAME STATUS AND PICTURE
		 * 
		 * -----------------------------------------------------------
		 */

		for (int i = 1; i <= numberOfPlayers; i++) {
			// Formatting decisions)

			/*---------- CHECK IF ACTIVE, IF SO, ADD TO DROPDOWN --------*/

			/* list contains labels so that the text colour can change */
			labelTempName = new LabelField(name, LabelField.ELLIPSIS) {
				public void paint(Graphics g) {
					g.setColor(Color.WHITE);
					super.paint(g);
				}
			};
			labelTempName.setFont(font2);

			labelTempTribe = new LabelField(tribe, LabelField.ELLIPSIS) {
				public void paint(Graphics g) {
					g.setColor(Color.WHITE);
					super.paint(g);
				}
			};
			labelTempName.setFont(font2);

			labelTempStatus = new LabelField(status, LabelField.ELLIPSIS) {
				public void paint(Graphics g) {
					g.setColor(Color.WHITE);
					super.paint(g);
				}
			};
			labelTempName.setFont(font2);

			list.add(new Object[] { picture, labelTempName, labelTempTribe,
					labelTempStatus });

			/* ------------------------------------------------------- */
			if (score > 0) // TESTING
				score--; // TESTING PURPOSES
			/* ------------------------------------------------------- */
		}

		HorizontalFieldManager horFieldManager = new HorizontalFieldManager(
				HorizontalFieldManager.USE_ALL_WIDTH
						| HorizontalFieldManager.FIELD_HCENTER) {
			// Override the paint method to draw the background image.
			public void paint(Graphics graphics) {
				// graphics.setColor(Color.GREEN);
				// graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
				super.paint(graphics);
			}
		};
		;

		/* Build the banner */

		/* BUILD THE CHOICES BASED OFF OF ACTIVE CONTESTANTS */
		String choices[] = { "Contestants", name, name, name, name, name, name,
				name, name, name, name, name, name, name };
		int iSetTo = 0;

		button1 = new ButtonField("Okay");
		button1.setChangeListener(this);

		/*
		 * ADD AFTER PERSISTANCE ------------------------------
		 * 
		 * CHECK IF THE PERSON HAS ALREADY VOTED IF THEY HAVE, TURN OFF BUTTON
		 * ACCESS
		 * 
		 * ---------------------------------------------------
		 */

		ObjectChoiceField tempField = new ObjectChoiceField(" Cast your "
				+ voteType + " vote: ", choices, iSetTo,
				ObjectChoiceField.FORCE_SINGLE_LINE
						| ObjectChoiceField.FIELD_HCENTER);
		horFieldManager.add(button1);
		horFieldManager.add(tempField);
		horFieldManager.setFont(font2);

		/* Build the components to MainScreen */
		this.setTitle(horFieldManager);
		this.add(vertFieldManager);
		this.setStatus(manager);
	}

	public void fieldChanged(Field arg0, int arg1) {
		if (arg0 == button1) { // if the okay button is clicked
			if (voteType.equals("weekly")) {
				// output
			} else if (voteType.equals("ultimate")) { // ultimate
				// output
			} else { // final
						// output
			}
		}
	}

}