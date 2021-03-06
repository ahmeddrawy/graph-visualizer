import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;


public class GUI extends JPanel {
    private static final long serialVersionUID = 1L;
    private ArrayList<JTextField> edgesTF;
    JTabbedPane tabbedPane;
    JPanel adjListPanel, visualsPanel, adjMatrixPanel, representationMatrixPanel, incidenceMatrixPanel;

    public GUI() {
        super(new GridLayout(1, 1));
        this.edgesTF = new ArrayList<>();

        tabbedPane = new JTabbedPane();
        
        JComponent setup = createSetupPanel();
        tabbedPane.addTab("Setup", setup);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        visualsPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Visual", visualsPanel);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setEnabledAt(1, false);
        
        adjListPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Adjacency List", adjListPanel);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        tabbedPane.setEnabledAt(2, false);
        
        adjMatrixPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Adjacency Matrix", adjMatrixPanel);
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setEnabledAt(3, false);
        
        representationMatrixPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Representation Matrix", representationMatrixPanel);
        tabbedPane.setMnemonicAt(4, KeyEvent.VK_5);
        tabbedPane.setEnabledAt(4, false);

        incidenceMatrixPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Incidence Matrix", incidenceMatrixPanel);
        tabbedPane.setMnemonicAt(5, KeyEvent.VK_6);
        tabbedPane.setEnabledAt(5, false);

        add(tabbedPane);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

    private JPanel createSetupPanel(){
        JPanel panel = new JPanel(false);
        JPanel form = new JPanel(new GridLayout(3,3));
        JPanel edges = new JPanel(false);
        edges.setAutoscrolls(true);
        JScrollPane edgesMain = new JScrollPane(edges); 
        edgesMain.setPreferredSize(new Dimension(600, 450));


        Integer[] numbers = new Integer[100];
        for(int i = 0; i < 100; ++i){
            numbers[i] = i+1;
        }
        JLabel nVerticesLabel = new JLabel("Select number of vertices");
        JComboBox nVertices = new JComboBox<>(numbers);
        JLabel nEdgesLabel = new JLabel("Select number of edges");
        JComboBox nEdges = new JComboBox<>(numbers);
        JLabel isDirectedLabel = new JLabel("Is it a directed graph?");
        JCheckBox isDirected = new JCheckBox();
        JButton generate = new JButton("Generate!");
        generate.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent event) {
                ArrayList<Edge> edgesArr = new ArrayList<>();
                Integer nV = (Integer) nVertices.getSelectedItem();
                boolean ok = true;
                // Loop over inputs 
                for(int i = 0; i < edgesTF.size(); i+=2){
                    JTextField u = edgesTF.get(i); 
                    JTextField v = edgesTF.get(i+1); 
                    try{
                        Edge e = new Edge(Integer.valueOf(u.getText()), Integer.valueOf(v.getText()));
                        // Check that there is no out of bounds vertices
                        if(e.getU() >= nV || e.getV() >= nV || e.getU() < 0 || e.getV() < 0){
                            ok = false;
                            break;
                        }
                        edgesArr.add(e);
                    }catch(NumberFormatException e){
                        ok = false;
                        break;
                    }
                }
                if(ok)
                    generateGraph(nV, edgesArr, isDirected.isSelected());
                else
                    JOptionPane.showMessageDialog(null, "Some nodes are not entered correctly");
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
        form.add(isDirectedLabel);
        form.add(isDirected);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(form);
        panel.add(edgesMain);
        panel.add(bottomPanel);
        return panel;
    }

    private JPanel createEdgesInput(int nEdges){
        edgesTF.clear();
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
            edgesTF.add(u);
            edgesTF.add(v);
        }
        return panel;
    }

    private void generateGraph(Integer nVertices, ArrayList<Edge> edges, boolean isDirected){
         IGraphBuilder graph = null;
        if(isDirected){
            graph = new DirectedGraphBuilder(nVertices, edges); 
        }else{
            graph = new UndirectedGraphBuilder(nVertices, edges); 
        }
        // Adjacency list
        graph.buildAdjacencyList();
        ArrayList<Integer>[] adjList = graph.getAdjacencyList();
        tabbedPane.setEnabledAt(2, true);
        adjListPanel.removeAll();
        adjListPanel.add(createAdjacencyListView(adjList));
        adjListPanel.repaint();
        // Visual
        try{
            Process p = Runtime.getRuntime().exec(new String[]{"python", "show_graph.py"});
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream(), StandardCharsets.UTF_8));
            bw.write(isDirected? "y" : "n");bw.newLine();
            bw.write(String.valueOf(nVertices));bw.newLine();
            bw.write(String.valueOf(edges.size()));bw.newLine();
            for(int i = 0; i < edges.size(); ++i){
                bw.write(String.valueOf(edges.get(i).getU()));bw.newLine();
                bw.write(String.valueOf(edges.get(i).getV()));bw.newLine();
            }
            bw.close();
            p.waitFor();
            // Show image
            BufferedImage myPicture = ImageIO.read(new File("graph.png"));
            JLabel picLabel = new JLabel(new ImageIcon(new ImageIcon(myPicture).getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH)));
            visualsPanel.removeAll();
            visualsPanel.add(picLabel);
            visualsPanel.repaint();
            tabbedPane.setEnabledAt(1, true);
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        // Adjacency Matrix
        graph.buildAdjacencyMatrix();
        boolean[][] adjMat = graph.getAdjacencyMatrix();
        tabbedPane.setEnabledAt(3, true);
        adjMatrixPanel.removeAll();
        adjMatrixPanel.add(createAdjacencyMatrixView(adjMat));
        adjMatrixPanel.repaint();

        // Representation matrix
        graph.buildRepresentationMatrix();
        int[][] representationMatrix = graph.getRepresentationMatrix();
        tabbedPane.setEnabledAt(4, true);
        representationMatrixPanel.removeAll();
        representationMatrixPanel.add(createRepresenationMatrixView(representationMatrix));
        representationMatrixPanel.repaint();

        // Incidence matrix
        graph.buildIncidenceMatrix();
        int[][] incidenceMatrix = graph.getIncidenceMatrix();
        tabbedPane.setEnabledAt(5, true);
        incidenceMatrixPanel.removeAll();
        incidenceMatrixPanel.add(createIncidenceMatrixView(incidenceMatrix));
        incidenceMatrixPanel.repaint();

    }

    private JPanel createIncidenceMatrixView(int[][] mat){
        JPanel panel = new JPanel(new GridLayout(mat.length+1, mat[0].length+1));
        int r = 0;
        int c = 0;
        for(int i = -1; i < mat.length; ++i){
            for(int j = -1; j < mat[0].length; ++j){
                if(i == -1 && j == -1){
                    panel.add(new JLabel(""));
                }else if(i == -1){
                    panel.add(new JLabel(String.valueOf(c++)));
                }else if(j == -1){
                    panel.add(new JLabel(String.valueOf(r++)));
                }else{
                    if(mat[i][j] == 1) //TODO: Why is it an int?
                        panel.add(new JLabel("YES"));
                    else
                        panel.add(new JLabel("NO"));
                }
            }
        }
        return panel;
    }

    private JPanel createAdjacencyMatrixView(boolean[][] mat){
        JPanel panel = new JPanel(new GridLayout(mat.length+1, mat.length+1));
        int r = 0;
        int c = 0;
        for(int i = -1; i < mat.length; ++i){
            for(int j = -1; j < mat.length; ++j){
                if(i == -1 && j == -1){
                    panel.add(new JLabel(""));
                }else if(i == -1){
                    panel.add(new JLabel(String.valueOf(c++)));
                }else if(j == -1){
                    panel.add(new JLabel(String.valueOf(r++)));
                }else{
                    if(mat[i][j])
                        panel.add(new JLabel("YES"));
                    else
                        panel.add(new JLabel("NO"));
                }
            }
        }
        return panel;
    }

    private JPanel createRepresenationMatrixView(int[][] mat){
        JPanel panel = new JPanel(new GridLayout(mat.length+1, mat.length+1));
        int r = 0;
        int c = 0;
        for(int i = -1; i < mat.length; ++i){
            for(int j = -1; j < mat.length; ++j){
                if(i == -1 && j == -1){
                    panel.add(new JLabel(""));
                }else if(i == -1){
                    panel.add(new JLabel(String.valueOf(c++)));
                }else if(j == -1){
                    panel.add(new JLabel(String.valueOf(r++)));
                }else{
                    panel.add(new JLabel(String.valueOf(mat[i][j])));
                }
            }
        }
        return panel;
    }

    private JPanel createAdjacencyListView(ArrayList<Integer>[] list){
        JPanel panel = new JPanel(new GridLayout(list.length, 2));
        for(int i = 0; i < list.length; ++i){
            JLabel src = new JLabel(String.valueOf(i));
            JLabel dst = new JLabel(sexyArrayList(list[i]));
            panel.add(src);
            panel.add(dst);
        }
        return panel;
    }

    private <T> String sexyArrayList(ArrayList<T> arr){
        String out = "[";
        for(int i = 0; i < arr.size(); ++i){
            out = out + arr.get(i);
            if(i != arr.size()-1)
                out += ",";
        }
        out = out + "]";
        return out;
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
