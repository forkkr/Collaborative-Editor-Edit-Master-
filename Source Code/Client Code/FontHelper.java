package Client;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FontHelper extends JDialog implements ActionListener, ListSelectionListener {

    JPanel panel1, panel2, panel3;
    JLabel fontLabel, sizeLabel, typeLabel, previewLabel;
    JTextField label, fontText, sizeText, typeText;
    JScrollPane fontScroll, sizeScroll, typeScroll;
    JList fontList, sizeList, typeList;
    JButton ok, cancel;
    GridBagLayout layout;
    GridBagConstraints layoutConstraints;

    public FontHelper() {
        setTitle("Choose Font");
        setSize(500, 600);
        setResizable(false);
        layout = new GridBagLayout();
        setLayout(layout);
        layoutConstraints = new GridBagConstraints();

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        fontLabel = new JLabel("Fonts:");
        getContentPane().add(fontLabel, layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        sizeLabel = new JLabel("Sizes:");
        getContentPane().add(sizeLabel, layoutConstraints);

        layoutConstraints.gridx = 3;
        layoutConstraints.gridy = 1;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        typeLabel = new JLabel("Types:");
        getContentPane().add(typeLabel, layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        fontText = new JTextField("Arial", 12);
        getContentPane().add(fontText, layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        sizeText = new JTextField("8", 4);
        getContentPane().add(sizeText, layoutConstraints);

        layoutConstraints.gridx = 3;
        layoutConstraints.gridy = 2;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        typeText = new JTextField("Regular", 6);
        getContentPane().add(typeText, layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontList = new JList(fonts);
        fontList.setFixedCellWidth(110);
        fontList.addListSelectionListener(this);
        fontList.setSelectedIndex(0);
        fontScroll = new JScrollPane(fontList);
        getContentPane().add(fontScroll, layoutConstraints);

        layoutConstraints.gridx = 2;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        String[] sizes = {"8", "10", "12", "14", "16", "18", "20", "22", "24"};
        sizeList = new JList(sizes);
        sizeList.setFixedCellWidth(20);
        sizeList.addListSelectionListener(this);
        sizeList.setSelectedIndex(0);
        sizeScroll = new JScrollPane(sizeList);
        getContentPane().add(sizeScroll , layoutConstraints);

        layoutConstraints.gridx = 3;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridheight = 1;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.WEST;
        String[] types = {"Regular", "Bold", "Italic", "Bold Italic"};
        typeList = new JList(types);
        typeList.setFixedCellWidth(60);
        typeList.addListSelectionListener(this);
        typeList.setSelectedIndex(0);
        typeScroll = new JScrollPane(typeList);
        getContentPane().add(typeScroll , layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 4;
        layoutConstraints.gridheight = 3;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());
        previewLabel = new JLabel("Preview: ");
        panel1.add(previewLabel);
        getContentPane().add(panel1, layoutConstraints);

        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 7;
        layoutConstraints.gridheight = 3;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        label = new JTextField("AaBbCcDdEeFfGgHhIiJiKk");
        label.setEditable(false);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        panel2.add(label);
        getContentPane().add(panel2, layoutConstraints);
        
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 10;
        layoutConstraints.gridheight = 3;
        layoutConstraints.gridwidth = 1;
        layoutConstraints.anchor = GridBagConstraints.CENTER;
        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        ok = new JButton("OK");
        cancel = new JButton("CANCEL");
        panel3.add(ok);
        panel3.add(cancel);
        getContentPane().add(panel3, layoutConstraints);
    }

    public Font getfont() {
        Font font = new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
        return font;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try
        {
            if(e.getSource() == fontList)
            {
                Font fontt = new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
                fontText.setText(String.valueOf(fontList.getSelectedValue()));
                label.setFont(fontt);
            }
            else if(e.getSource() == sizeList)
            {
                Font fontsize = new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
                sizeText.setText(String.valueOf(sizeList.getSelectedValue()));
                label.setFont(fontsize);
            }
            else
            {
                Font fonttype = new Font(String.valueOf(fontList.getSelectedValue()), typeList.getSelectedIndex(), Integer.parseInt(String.valueOf(sizeList.getSelectedValue())));
                typeText.setText(String.valueOf(typeList.getSelectedValue()));
                label.setFont(fonttype);
            }
        }
        catch(Exception ee)
        {
            
        }
    }

    public JButton getOk() {
        return ok;
    }

    public JButton getCancel() {
        return cancel;
    }
}
