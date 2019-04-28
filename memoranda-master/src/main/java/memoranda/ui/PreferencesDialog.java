package main.java.memoranda.ui;

import java.io.File;
import java.util.Vector;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import main.java.memoranda.util.Configuration;
import main.java.memoranda.util.CurrentStorage;
import main.java.memoranda.util.Local;
import main.java.memoranda.util.MimeTypesList;

import java.awt.event.*;

// Code Smells Assignemtn 7 task 3
// TASK 3-1 SMELLS WITHIN A CLASS
//Large class Refactor to create smaller classes

/*$Id: PreferencesDialog.java,v 1.16 2006/06/28 22:58:31 alexeya Exp $*/
public class PreferencesDialog extends JDialog {
	JPanel topPanel = new JPanel(new BorderLayout());

	JTabbedPane tabbedPanel = new JTabbedPane();

	JPanel GeneralPanel = new JPanel(new GridBagLayout());

	GridBagConstraints gbc;

	JLabel jLabel1 = new JLabel();

	ButtonGroup minGroup = new ButtonGroup();

	JRadioButton minTaskbarRB = new JRadioButton();

	JRadioButton minHideRB = new JRadioButton();

	ButtonGroup closeGroup = new ButtonGroup();

	JLabel jLabel2 = new JLabel();

	JRadioButton closeExitRB = new JRadioButton();

	JCheckBox askConfirmChB = new JCheckBox();

	JRadioButton closeHideRB = new JRadioButton();

	JLabel jLabel3 = new JLabel();

	ButtonGroup lfGroup = new ButtonGroup();

	JRadioButton lfSystemRB = new JRadioButton();

	JRadioButton lfJavaRB = new JRadioButton();

	JRadioButton lfCustomRB = new JRadioButton();

	JLabel classNameLabel = new JLabel();

	JTextField lfClassName = new JTextField();

	JLabel jLabel4 = new JLabel();

	JCheckBox enSystrayChB = new JCheckBox();

	JCheckBox startMinimizedChB = new JCheckBox();

	JCheckBox enSplashChB = new JCheckBox();

	JCheckBox enL10nChB = new JCheckBox();

	JCheckBox firstdow = new JCheckBox();

	JPanel resourcePanel = new JPanel(new BorderLayout());

	ResourceTypePanel resourceTypePanel = new ResourceTypePanel();

	Border rstPanelBorder;

	JPanel rsBottomPanel = new JPanel(new GridBagLayout());

	TitledBorder rsbpBorder;

	JButton okB = new JButton();

	JButton cancelB = new JButton();

	JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

	JLabel jLabel5 = new JLabel();

	JTextField browserPath = new JTextField();

	JButton browseB = new JButton();

	JLabel lblExit = new JLabel();

	JPanel soundPanel = new JPanel();

	JCheckBox enableSoundCB = new JCheckBox();

	BorderLayout borderLayout1 = new BorderLayout();

	TitledBorder titledBorder1;

	ButtonGroup soundGroup = new ButtonGroup();

	JPanel jPanel2 = new JPanel();

	JButton soundFileBrowseB = new JButton();

	GridLayout gridLayout1 = new GridLayout();

	JPanel jPanel1 = new JPanel();

	JRadioButton soundBeepRB = new JRadioButton();

	JLabel jLabel6 = new JLabel();

	JTextField soundFile = new JTextField();

	JRadioButton soundDefaultRB = new JRadioButton();

	BorderLayout borderLayout3 = new BorderLayout();

	JPanel jPanel3 = new JPanel();

	JRadioButton soundCustomRB = new JRadioButton();

	BorderLayout borderLayout2 = new BorderLayout();
	
	JPanel editorConfigPanel = new JPanel(new BorderLayout());
	JPanel econfPanel = new JPanel(new GridLayout(5, 2));
	Vector fontnames = getFontNames();
	JComboBox normalFontCB = new JComboBox(fontnames);
	JComboBox headerFontCB = new JComboBox(fontnames);
	JComboBox monoFontCB = new JComboBox(fontnames);
	JSpinner baseFontSize = new JSpinner();
	JCheckBox antialiasChB = new JCheckBox();
	JLabel normalFontLabel = new JLabel();
	JLabel headerFontLabel = new JLabel();
	JLabel monoFontLabel = new JLabel();
	JLabel baseFontSizeLabel = new JLabel();

	public PreferencesDialog(Frame frame) {
		super(frame, Local.getString("Preferences"), true);
		try {
			resourceTypePanel.buildEditorConfigPanel(this);
		} catch (Exception ex) {
			new ExceptionDialog(ex);
		}
	}

	public PreferencesDialog() {
		this(null);
	}

	void setValues() {
		enL10nChB.setSelected(!Configuration.get("DISABLE_L10N").toString()
				.equalsIgnoreCase("yes"));
		enSplashChB.setSelected(!Configuration.get("SHOW_SPLASH").toString()
				.equalsIgnoreCase("no"));
		enSystrayChB.setSelected(!Configuration.get("DISABLE_SYSTRAY")
				.toString().equalsIgnoreCase("yes"));
		startMinimizedChB.setSelected(Configuration.get("START_MINIMIZED")
				.toString().equalsIgnoreCase("yes"));
		firstdow.setSelected(Configuration.get("FIRST_DAY_OF_WEEK").toString()
				.equalsIgnoreCase("mon"));

		enableCustomLF(false);
		String lf = Configuration.get("LOOK_AND_FEEL").toString();
		if (lf.equalsIgnoreCase("system"))
			lfSystemRB.setSelected(true);
		else if (lf.equalsIgnoreCase("default"))
			lfJavaRB.setSelected(true);
		else if (lf.length() > 0) {
			lfCustomRB.setSelected(true);
			enableCustomLF(true);
			lfClassName.setText(lf);
		} else
			lfJavaRB.setSelected(true);

		askConfirmChB.setSelected(!Configuration.get("ASK_ON_EXIT").toString()
				.equalsIgnoreCase("no"));
		String onclose = Configuration.get("ON_CLOSE").toString();
		if (onclose.equals("exit")) {
			this.closeExitRB.setSelected(true);
			// this.askConfirmChB.setEnabled(true);
		} else {
			this.closeHideRB.setSelected(true);
			// this.askConfirmChB.setEnabled(false);
		}

		String onmin = Configuration.get("ON_MINIMIZE").toString();
		this.minTaskbarRB.setSelected(true);

		if (!System.getProperty("os.name").startsWith("Win"))
			this.browserPath.setText(MimeTypesList.getAppList()
					.getBrowserExec());
		if (Configuration.get("NOTIFY_SOUND").equals("")) {
			Configuration.put("NOTIFY_SOUND", "DEFAULT");
		}

		boolean enableSnd = !Configuration.get("NOTIFY_SOUND").toString()
				.equalsIgnoreCase("DISABLED");
		enableSoundCB.setSelected(enableSnd);
		if (Configuration.get("NOTIFY_SOUND").toString().equalsIgnoreCase(
				"DEFAULT")
				|| Configuration.get("NOTIFY_SOUND").toString()
						.equalsIgnoreCase("DISABLED")) {
			this.soundDefaultRB.setSelected(true);
			this.enableCustomSound(false);
		} else if (Configuration.get("NOTIFY_SOUND").toString()
				.equalsIgnoreCase("BEEP")) {
			this.soundBeepRB.setSelected(true);
			this.enableCustomSound(false);
		} else {
			System.out.println(Configuration.get("NOTIFY_SOUND").toString());
			this.soundCustomRB.setSelected(true);
			this.soundFile
					.setText(Configuration.get("NOTIFY_SOUND").toString());
			this.enableCustomSound(true);
		}
		this.enableSound(enableSnd);
		
		antialiasChB.setSelected(Configuration.get("ANTIALIAS_TEXT")
				.toString().equalsIgnoreCase("yes"));
		if (Configuration.get("NORMAL_FONT").toString().length() >0)
			normalFontCB.setSelectedItem(Configuration.get("NORMAL_FONT").toString());
		else
			normalFontCB.setSelectedItem("serif");
		if (Configuration.get("HEADER_FONT").toString().length() >0)
			headerFontCB.setSelectedItem(Configuration.get("HEADER_FONT").toString());
		else
			headerFontCB.setSelectedItem("sans-serif");
		if (Configuration.get("MONO_FONT").toString().length() >0)
			monoFontCB.setSelectedItem(Configuration.get("MONO_FONT").toString());
		else
			monoFontCB.setSelectedItem("monospaced");
		if (Configuration.get("BASE_FONT_SIZE").toString().length() >0)
			baseFontSize.setValue(Integer.decode(Configuration.get("BASE_FONT_SIZE").toString()));
		else
			baseFontSize.setValue(new Integer(16));
	}

	void apply() {
		if (this.firstdow.isSelected())
			Configuration.put("FIRST_DAY_OF_WEEK", "mon");
		else
			Configuration.put("FIRST_DAY_OF_WEEK", "sun");

		if (this.enL10nChB.isSelected())
			Configuration.put("DISABLE_L10N", "no");
		else
			Configuration.put("DISABLE_L10N", "yes");

		if (this.enSplashChB.isSelected())
			Configuration.put("SHOW_SPLASH", "yes");
		else
			Configuration.put("SHOW_SPLASH", "no");

		if (this.enSystrayChB.isSelected())
			Configuration.put("DISABLE_SYSTRAY", "no");
		else
			Configuration.put("DISABLE_SYSTRAY", "yes");

		if (this.startMinimizedChB.isSelected())
			Configuration.put("START_MINIMIZED", "yes");
		else
			Configuration.put("START_MINIMIZED", "no");

		if (this.askConfirmChB.isSelected())
			Configuration.put("ASK_ON_EXIT", "yes");
		else
			Configuration.put("ASK_ON_EXIT", "no");

		if (this.closeExitRB.isSelected())
			Configuration.put("ON_CLOSE", "exit");
		else
			Configuration.put("ON_CLOSE", "minimize");

		Configuration.put("ON_MINIMIZE", "normal");

		String lf = Configuration.get("LOOK_AND_FEEL").toString();
		String newlf = "";

		if (this.lfSystemRB.isSelected())
			newlf = "system";
		else if (this.lfJavaRB.isSelected())
			newlf = "default";
		else if (this.lfCustomRB.isSelected())
			newlf = this.lfClassName.getText();

		if (!lf.equalsIgnoreCase(newlf)) {
			Configuration.put("LOOK_AND_FEEL", newlf);
			try {
				if (Configuration.get("LOOK_AND_FEEL").equals("system"))
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				else if (Configuration.get("LOOK_AND_FEEL").equals("default"))
					UIManager.setLookAndFeel(UIManager
							.getCrossPlatformLookAndFeelClassName());
				else if (Configuration.get("LOOK_AND_FEEL").toString().length() > 0)
					UIManager.setLookAndFeel(Configuration.get("LOOK_AND_FEEL")
							.toString());

				SwingUtilities.updateComponentTreeUI(App.getFrame());

			} catch (Exception e) {
				Configuration.put("LOOK_AND_FEEL", lf);
				new ExceptionDialog(
						e,
						"Error when initializing a pluggable look-and-feel. Default LF will be used.",
						"Make sure that specified look-and-feel library classes are on the CLASSPATH.");
			}
		}
		String brPath = this.browserPath.getText();
		if (new java.io.File(brPath).isFile()) {
			MimeTypesList.getAppList().setBrowserExec(brPath);
			CurrentStorage.get().storeMimeTypesList();
		}

		if (!this.enableSoundCB.isSelected())
			Configuration.put("NOTIFY_SOUND", "DISABLED");
		else if (this.soundDefaultRB.isSelected())
			Configuration.put("NOTIFY_SOUND", "DEFAULT");
		else if (this.soundBeepRB.isSelected())
			Configuration.put("NOTIFY_SOUND", "BEEP");
		else if ((this.soundCustomRB.isSelected())
				&& (this.soundFile.getText().trim().length() > 0))
			Configuration.put("NOTIFY_SOUND", this.soundFile.getText().trim());

		if (antialiasChB.isSelected())
			Configuration.put("ANTIALIAS_TEXT", "yes");
		else
			Configuration.put("ANTIALIAS_TEXT", "no");
		
		Configuration.put("NORMAL_FONT", normalFontCB.getSelectedItem());
		Configuration.put("HEADER_FONT", headerFontCB.getSelectedItem());
		Configuration.put("MONO_FONT", monoFontCB.getSelectedItem());
		Configuration.put("BASE_FONT_SIZE", baseFontSize.getValue());
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.editor.setAntiAlias(antialiasChB.isSelected());
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.initCSS();
		App.getFrame().workPanel.dailyItemsPanel.editorPanel.editor.repaint();
		
		Configuration.saveConfig();
		
	}

	void enableCustomLF(boolean is) {
		this.classNameLabel.setEnabled(is);
		this.lfClassName.setEnabled(is);
	}

	void enableCustomSound(boolean is) {
		this.soundFile.setEnabled(is);
		this.soundFileBrowseB.setEnabled(is);
		this.jLabel6.setEnabled(is);
	}

	void enableSound(boolean is) {
		this.soundDefaultRB.setEnabled(is);
		this.soundBeepRB.setEnabled(is);
		this.soundCustomRB.setEnabled(is);
		enableCustomSound(is);

		this.soundFileBrowseB.setEnabled(is && soundCustomRB.isSelected());
		this.soundFile.setEnabled(is && soundCustomRB.isSelected());
		this.jLabel6.setEnabled(is && soundCustomRB.isSelected());

	}

	void okB_actionPerformed(ActionEvent e) {
		apply();
		this.dispose();
	}

	void cancelB_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void minTaskbarRB_actionPerformed(ActionEvent e) {

	}

	void minHideRB_actionPerformed(ActionEvent e) {

	}

	void closeExitRB_actionPerformed(ActionEvent e) {
		// this.askConfirmChB.setEnabled(true);
	}

	void askConfirmChB_actionPerformed(ActionEvent e) {

	}

	void closeHideRB_actionPerformed(ActionEvent e) {
		// this.askConfirmChB.setEnabled(false);
	}

	void lfSystemRB_actionPerformed(ActionEvent e) {
		this.enableCustomLF(false);
	}

	void lfJavaRB_actionPerformed(ActionEvent e) {
		this.enableCustomLF(false);
	}

	void lfCustomRB_actionPerformed(ActionEvent e) {
		this.enableCustomLF(true);
	}

	void enSystrayChB_actionPerformed(ActionEvent e) {

	}

	void enSplashChB_actionPerformed(ActionEvent e) {

	}

	void enL10nChB_actionPerformed(ActionEvent e) {

	}

	void browseB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put("FileChooser.lookInLabelText", Local
				.getString("Look in:"));
		UIManager.put("FileChooser.upFolderToolTipText", Local
				.getString("Up One Level"));
		UIManager.put("FileChooser.newFolderToolTipText", Local
				.getString("Create New Folder"));
		UIManager.put("FileChooser.listViewButtonToolTipText", Local
				.getString("List"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
				.getString("Details"));
		UIManager.put("FileChooser.fileNameLabelText", Local
				.getString("File Name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText", Local
				.getString("Files of Type:"));
		UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
		UIManager.put("FileChooser.openButtonToolTipText", Local
				.getString("Open selected file"));
		UIManager
				.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
		UIManager.put("FileChooser.cancelButtonToolTipText", Local
				.getString("Cancel"));

		JFileChooser chooser = new JFileChooser();
		chooser.setFileHidingEnabled(false);
		chooser.setDialogTitle(Local
				.getString("Select the web-browser executable"));
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setPreferredSize(new Dimension(550, 375));
		if (System.getProperty("os.name").startsWith("Win")) {
			chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.EXE));
			chooser.setCurrentDirectory(new File("C:\\Program Files"));
		}
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			this.browserPath.setText(chooser.getSelectedFile().getPath());
	}

	void enableSoundCB_actionPerformed(ActionEvent e) {
		enableSound(enableSoundCB.isSelected());
	}

	void soundFileBrowseB_actionPerformed(ActionEvent e) {
		// Fix until Sun's JVM supports more locales...
		UIManager.put("FileChooser.lookInLabelText", Local
				.getString("Look in:"));
		UIManager.put("FileChooser.upFolderToolTipText", Local
				.getString("Up One Level"));
		UIManager.put("FileChooser.newFolderToolTipText", Local
				.getString("Create New Folder"));
		UIManager.put("FileChooser.listViewButtonToolTipText", Local
				.getString("List"));
		UIManager.put("FileChooser.detailsViewButtonToolTipText", Local
				.getString("Details"));
		UIManager.put("FileChooser.fileNameLabelText", Local
				.getString("File Name:"));
		UIManager.put("FileChooser.filesOfTypeLabelText", Local
				.getString("Files of Type:"));
		UIManager.put("FileChooser.openButtonText", Local.getString("Open"));
		UIManager.put("FileChooser.openButtonToolTipText", Local
				.getString("Open selected file"));
		UIManager
				.put("FileChooser.cancelButtonText", Local.getString("Cancel"));
		UIManager.put("FileChooser.cancelButtonToolTipText", Local
				.getString("Cancel"));

		JFileChooser chooser = new JFileChooser();
		chooser.setFileHidingEnabled(false);
		chooser.setDialogTitle(Local.getString("Select the sound file"));
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setPreferredSize(new Dimension(550, 375));
		chooser.setFileFilter(new AllFilesFilter(AllFilesFilter.WAV));
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			this.soundFile.setText(chooser.getSelectedFile().getPath());
	}

	void soundDefaultRB_actionPerformed(ActionEvent e) {
		this.enableCustomSound(false);
	}

	void soundBeepRB_actionPerformed(ActionEvent e) {
		this.enableCustomSound(false);
	}

	void soundCustomRB_actionPerformed(ActionEvent e) {
		this.enableCustomSound(true);
	}
	
	Vector getFontNames() {
		GraphicsEnvironment gEnv = 
        	GraphicsEnvironment.getLocalGraphicsEnvironment();
        String envfonts[] = gEnv.getAvailableFontFamilyNames();
        Vector fonts = new Vector();
        fonts.add("serif");
        fonts.add("sans-serif");
        fonts.add("monospaced");
        for (int i = 0; i < envfonts.length; i++)
            fonts.add(envfonts[i]);
		return fonts;
	}
}