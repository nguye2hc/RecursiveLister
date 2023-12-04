import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

public class RecursiveListerFrame extends JFrame{
    JLabel guiLbl;
    JPanel mainPnl,topPnl,centerPnl,bottomPnl;
    JTextArea result;
    JScrollPane scroller;
    JButton startBtn, quitBtn;
    JFileChooser chooser = new JFileChooser();
    File workingDirectory = new File(System.getProperty("user.dir"));
    public RecursiveListerFrame()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();

        int height = screen.height;
        int width = screen.width;

        setSize(width/3,height/2);
        setLocation(width/3,height/6);
        setTitle("Recursive Lister");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createGUI();
        setVisible(true);
    }
    public void createGUI()
    {
        mainPnl = new JPanel();

        createTopPnl();
        createCenterPnl();
        createBottomPnl();

        mainPnl.setLayout(new BorderLayout());
        mainPnl.add(topPnl,BorderLayout.NORTH);
        mainPnl.add(centerPnl);
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);

        add(mainPnl);
    }
    private void  createTopPnl()
    {
        topPnl = new JPanel();
        guiLbl = new JLabel("Recursive Lister");
        guiLbl.setFont(new Font("Arial",Font.PLAIN,24));
        topPnl.add(guiLbl);
    }
    private void createCenterPnl()
    {
        centerPnl = new JPanel();
        centerPnl.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1),"File List:"));
        result = new JTextArea(20,35);
        result.setLineWrap(true);
        result.setEditable(false);
        scroller = new JScrollPane(result);
        centerPnl.add(scroller);
    }
    private void createBottomPnl()
    {
        bottomPnl = new JPanel();

        startBtn = new JButton("Start");
        startBtn.addActionListener(e -> {
            chooser.setCurrentDirectory(workingDirectory);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)            {
                File selectedFile = chooser.getSelectedFile();
                result.setText("Current Directory: " + selectedFile + "\n\n");
                result.append("Sub directory and files: " + "\n");

                listFile(selectedFile);
            }else
            {
                JOptionPane.showMessageDialog(null,"No files found","Information",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        startBtn.setFont(new Font("Arial",Font.BOLD,14));

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(e ->
        {
            System.exit(0);
        });
        quitBtn.setFont(new Font("Arial",Font.BOLD,14));

        bottomPnl.add(startBtn);
        bottomPnl.add(quitBtn);
    }
    private void listFile(File dir)
    {
        File[] files = dir.listFiles();
        if(files!= null)
        {
            for(File f : files)
            {
                if(f.isDirectory())
                {
                    listFile(f);
                }
                else {
                    result.append(f.getPath()+"\n");
                }
            }
        }
    }
}