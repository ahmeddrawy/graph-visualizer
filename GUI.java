import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JPanel {
    private static final long serialVersionUID = 1L;

    public GUI() {
        super(new GridLayout(1, 1));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        JComponent setup = createSetupPanel();
        tabbedPane.addTab("Setup", setup);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JComponent visual = makeTextPanel("Panel #2");
        tabbedPane.addTab("Visual", visual);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setEnabledAt(1, false);
        
        JComponent adjList = makeTextPanel("Panel #3");
        tabbedPane.addTab("Adjacency List", adjList);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.setEnabledAt(2, false);
        
        JComponent adjMatrix = makeTextPanel( "Panel #4");
        adjMatrix.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Adjacency Matrix", adjMatrix);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setEnabledAt(3, false);
        
        add(tabbedPane);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private JPanel createSetupPanel(){
        JPanel panel = new JPanel(false);
        JPanel form = new JPanel(new GridLayout(2,2));
        JPanel edges = new JPanel(false);
        edges.setAutoscrolls(true);
        JScrollPane edgesMain = new JScrollPane(edges); 
        edgesMain.setPreferredSize(new Dimension(200, 200));


        Integer[] numbers = new Integer[100];
        for(int i = 0; i < 100; ++i){
            numbers[i] = i+1;
        }
        JLabel nVerticesLabel = new JLabel("Select number of vertices");
        JComboBox nVertices = new JComboBox<>(numbers);
        JLabel nEdgesLabel = new JLabel("Select number of edges");
        JComboBox nEdges = new JComboBox<>(numbers);
        JButton generate = new JButton("Generate!");
        generate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                generateGraph();
            }
        });
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(generate);

        nEdges.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                edges.removeAll();
                edges.add(createEdgesInput((Integer) nEdges.getSelectedItem()));
                edges.revalidate();
                edges.repaint();
            }
        });

        form.add(nVerticesLabel);
        form.add(nVertices);
        form.add(nEdgesLabel);
        form.add(nEdges);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(form);
        panel.add(edgesMain);
        panel.add(bottomPanel);
        return panel;
    }

    private JPanel createEdgesInput(int nEdges){
        JPanel panel = new JPanel(new GridLayout(nEdges+1, 2));
        panel.add(new JLabel("From"));
        panel.add(new JLabel("To"));
        Dimension size = new Dimension(50, 20);
        for(int i = 0; i < nEdges; ++i){
            JTextField u = new JTextField();
            JTextField v = new JTextField();
            u.setPreferredSize(size);
            v.setPreferredSize(size);
            panel.add(u);
            panel.add(v);
        }
        return panel;
    }

    private void generateGraph(){

    }

    private JPanel createBoxRow(JComponent label, JComponent val){
        JPanel row = new JPanel(false);
        row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
        row.add(label);
        row.add(Box.createHorizontalGlue());
        row.add(val);
        return row;
    }
    
    protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Graph helper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(new GUI(), BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        createAndShowGUI();
            }
        });
    }
}
