import  javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class A19007 extends JDialog
{
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel lblTitle;
    private JComboBox cboLocation;
    private JComboBox cboAltitude;
    private JLabel lblWindSpeed;
    private JLabel lblWindTemp;
    private JLabel lblWindDir;
    public static Stations db = new Stations();
    String[] aryAltitudes = {"03000","06000","09000","12000","18000","24000","30000","34000","36000"};
    /**
     * Listener to set up program
     */
    public A19007()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });
// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });
// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    /**
     * Ok click event asseses index position of combo boxes and uses that number in the stations and altitudes arrays to
     * return correct weather specs for selected location and altitude.
     */
    private void onOK()
    {
        int intLocIndex=  cboLocation.getSelectedIndex();
        int intAltIndex = cboAltitude.getSelectedIndex();
        String strStaWeather = db.getStaWea(intLocIndex);
        NWSFB fb = new NWSFB(strStaWeather);
        fb.updateString(strStaWeather);
        lblWindDir.setText(fb.getWindDir(aryAltitudes[intAltIndex]));
        lblWindSpeed.setText(fb.getWindSpeed(aryAltitudes[intAltIndex]));
        lblWindTemp.setText(fb.getWindTemp(aryAltitudes[intAltIndex]));
    }
    /**
     * onCancel closes the program
     */
    private void onCancel()
    {
// add your code here if necessary
        dispose();
    }
    /**
     * this method when called will populate the altitude combobox     */
    private void populateAltitudes()
    {
        for(int i=0; i< 9; i++)
        {
            cboAltitude.addItem(aryAltitudes[i]);
        }
    }
    /**
     * this method when called will populate the location combobox     */
    private void populateLocations() throws Exception
    {
        int intLength = db.Length();
        for(int i=0; i< intLength; i++)
        {
            cboLocation.addItem(db.getStaID(i));
        }

    }
    /**
     * this is the main method that sets up the form nice and pretty.  */
    public static void main(String[] args)throws Exception
    {
        A19007 dialog = new A19007();
        //resize form
        final int WINDOW_WIDTH = 550;
        final int WINDOW_HEIGHT = 250;
        dialog.contentPane.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        dialog.populateAltitudes();
        dialog.populateLocations();
        db.Length();
        //set form title
        dialog.setTitle("C.Dorich A19007");
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
