package demos;

import datastructures.trees.BinarySearchTree;
// We can't import BSTNode since it's package-private, unless we made it public.
// Assuming it's accessible or we just write it exactly translated.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class BSTVisualizer extends JFrame implements ActionListener {
    private BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    // For locations, we will just use Object for the node references if they aren't public
    private HashMap<Object, Rectangle> nodeLocations = new HashMap<>();
    private HashMap<Object, Dimension> subtreeSizes = new HashMap<>();
    private boolean dirty = true;
    private int parent2child = 15;
    private int child2child = 24;
    private Dimension empty = new Dimension(0, 0);
    private FontMetrics fm = null;
    private JMenuBar bar;
    private JButton btnBalanced;
    private JButton btnDegenerate;
    private JButton btnRandom;
    private JButton btnInsert;
    private JButton btnRemove;
    private JButton btnRemoveMin;
    private JButton btnRebuild;
    private JButton btnIn;
    private JButton btnPre;
    private JButton btnPost;
    private JButton btnLevel;

    public BSTVisualizer() {
        super("Binary Search Tree Test");
        this.setLayout(new BorderLayout());
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        this.bar = new JMenuBar();
        this.bar.setBorder(BorderFactory.createBevelBorder(0));
        this.createTopMenu();
        bottomPanel.add(this.bar, "North");
        this.bar = new JMenuBar();
        this.bar.setBorder(BorderFactory.createBevelBorder(0));
        this.createBottomMenu();
        bottomPanel.add(this.bar, "South");
        this.add(bottomPanel, "South");
        this.setSize(840, 600);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        this.repaint();
    }

    private void createTopMenu() {
        this.bar.add(new JSeparator(0));
        this.btnBalanced = this.createButton("Generate balanced BST", Color.black);
        this.btnDegenerate = this.createButton("Generate degenerate BST", Color.black);
        this.btnRandom = this.createButton("Generate random BST", Color.black);
        this.bar.add(new JSeparator(0));
        this.btnRemove = this.createButton("Remove", Color.red);
        this.btnRemoveMin = this.createButton("RemoveMin", Color.red);
        this.btnInsert = this.createButton("Insert", Color.darkGray);
        this.bar.add(new JSeparator(0));
    }

    private void createBottomMenu() {
        this.bar.add(new JSeparator(0));
        this.btnRebuild = this.createButton("Rebuild balanced", Color.blue);
        this.bar.add(new JSeparator(0));
        this.btnIn = this.createButton("In-Order", Color.GREEN.darker());
        this.btnPre = this.createButton("Pre-Order", Color.GREEN.darker());
        this.btnPost = this.createButton("Post-Order", Color.GREEN.darker());
        this.btnLevel = this.createButton("By levels", Color.GREEN.darker());
        this.bar.add(new JSeparator(0));
    }

    private JButton createButton(String text, Color c) {
        JButton aux = new JButton(text);
        aux.setForeground(c);
        aux.addActionListener(this);
        this.bar.add(aux);
        return aux;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.btnBalanced) {
                this.generateBalancedBST();
            } else if (e.getSource() == this.btnDegenerate) {
                this.generateDegenerateBST();
            } else if (e.getSource() == this.btnRandom) {
                this.generateRandomBST();
            } else if (e.getSource() == this.btnRemove) {
                Integer s = this.readNode("Enter the data to remove");
                if (s != null) {
                    this.tree.remove(s);
                }
            } else if (e.getSource() == this.btnRemoveMin) {
                Integer s = (Integer)this.tree.removeMin();
                JOptionPane.showMessageDialog(this, "Minimum = " + s.toString(), "Result", 1);
            } else if (e.getSource() == this.btnInsert) {
                Integer s = this.readNode("Enter the data to insert");
                if (s != null) {
                    this.tree.insert(s);
                }
            } else if (e.getSource() == this.btnRebuild) {
                this.tree.rebalance();
            } else if (e.getSource() == this.btnIn) {
                JOptionPane.showMessageDialog(this, this.tree.toStringInOrder(), "In-Order Traversal", -1);
            } else if (e.getSource() == this.btnPre) {
                JOptionPane.showMessageDialog(this, this.tree.toStringPreOrder(), "Pre-Order Traversal", -1);
            } else if (e.getSource() == this.btnPost) {
                JOptionPane.showMessageDialog(this, this.tree.toStringPostOrder(), "Post-Order Traversal", -1);
            } else if (e.getSource() == this.btnLevel) {
                JOptionPane.showMessageDialog(this, this.tree.toStringByLevel(), "Level Traversal", -1);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex, "Exception detected:", 0);
        }

        this.dirty = true;
        this.repaint();
    }

    private void generateBalancedBST() {
        this.tree = new BinarySearchTree<>();
        int[] v = this.generateRandomArray(15);
        Arrays.sort(v);
        this.insertBalanced(v, 0, v.length - 1);
    }

    private void generateDegenerateBST() {
        this.tree = new BinarySearchTree<>();
        int[] v = this.generateRandomArray(10);
        Arrays.sort(v);

        for(int i = 0; i < v.length; ++i) {
            this.tree.insert(v[i]);
        }
    }

    private void generateRandomBST() {
        Random rnd = new Random();

        do {
            this.tree = new BinarySearchTree<>();
            int size = rnd.nextInt(26) + 5;
            int[] v = this.generateRandomArray(size);

            for(int i = 0; i < v.length; ++i) {
                this.tree.insert(v[i]);
            }
        } while(height(getRoot(this.tree)) > 10);
    }
    
    private Object getRoot(BinarySearchTree<Integer> t) {
        try {
            java.lang.reflect.Field f = t.getClass().getDeclaredField("root");
            f.setAccessible(true);
            return f.get(t);
        } catch(Exception e) { return null; }
    }
    private Object getLeft(Object n) {
        try {
            java.lang.reflect.Field f = n.getClass().getDeclaredField("left");
            f.setAccessible(true);
            return f.get(n);
        } catch(Exception e) { return null; }
    }
    private Object getRight(Object n) {
        try {
            java.lang.reflect.Field f = n.getClass().getDeclaredField("right");
            f.setAccessible(true);
            return f.get(n);
        } catch(Exception e) { return null; }
    }
    private Object getData(Object n) {
        try {
            java.lang.reflect.Field f = n.getClass().getDeclaredField("data");
            f.setAccessible(true);
            return f.get(n);
        } catch(Exception e) { return null; }
    }

    private int height(Object current) {
        if (current == null) {
            return 0;
        } else {
            return getLeft(current) == null && getRight(current) == null ? 0 : 1 + Math.max(height(getLeft(current)), height(getRight(current)));
        }
    }

    private int[] generateRandomArray(int size) {
        Random rnd = new Random();
        boolean[] exists = new boolean[100];
        int[] v = new int[size];
        int i = 0;

        while(i < v.length) {
            v[i] = rnd.nextInt(100);
            if (!exists[v[i]]) {
                exists[v[i]] = true;
                ++i;
            }
        }

        return v;
    }

    private void insertBalanced(int[] v, int left, int right) {
        if (left <= right) {
            int m = (left + right) / 2;
            this.tree.insert(v[m]);
            this.insertBalanced(v, left, m - 1);
            this.insertBalanced(v, m + 1, right);
        }
    }

    private Integer readNode(String text) {
        Integer n = null;

        String c;
        do {
            c = JOptionPane.showInputDialog(text);
            if (c != null) {
                try {
                    n = Integer.parseInt(c);
                } catch (NumberFormatException var5) {
                    JOptionPane.showMessageDialog(this, "You have not entered a valid integer", "Error", 0);
                }
            }
        } while(n == null && c != null);

        return n;
    }

    private void calculateLocations() {
        this.nodeLocations.clear();
        this.subtreeSizes.clear();
        Object root = getRoot(this.tree);
        if (root != null) {
            this.calculateSubtreeSize(root);
            this.calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    private Dimension calculateSubtreeSize(Object n) {
        if (n == null) {
            return new Dimension(0, 0);
        } else {
            Dimension ld = this.calculateSubtreeSize(getLeft(n));
            Dimension rd = this.calculateSubtreeSize(getRight(n));
            int h = 2 * this.fm.getHeight() + this.parent2child + Math.max(ld.height, rd.height);
            int w = ld.width + this.child2child + rd.width;
            Dimension d = new Dimension(w, h);
            this.subtreeSizes.put(n, d);
            return d;
        }
    }

    private void calculateLocation(Object n, int left, int right, int top) {
        if (n != null) {
            Dimension ld = (Dimension)this.subtreeSizes.get(getLeft(n));
            if (ld == null) {
                ld = this.empty;
            }

            Dimension rd = (Dimension)this.subtreeSizes.get(getRight(n));
            if (rd == null) {
                rd = this.empty;
            }

            int center = 0;
            if (right != Integer.MAX_VALUE) {
                center = right - rd.width - this.child2child / 2;
            } else if (left != Integer.MAX_VALUE) {
                center = left + ld.width + this.child2child / 2;
            }

            int width = this.fm.stringWidth("" + getData(n));
            Rectangle r = new Rectangle(center - width / 2 - 3, top + 25, width + 6, this.fm.getHeight());
            this.nodeLocations.put(n, r);
            this.calculateLocation(getLeft(n), Integer.MAX_VALUE, center - this.child2child / 2, top + 2 * this.fm.getHeight() + this.parent2child);
            this.calculateLocation(getRight(n), center + this.child2child / 2, Integer.MAX_VALUE, top + 2 * this.fm.getHeight() + this.parent2child);
        }
    }

    private void drawTree(Graphics2D g, Object n, int px, int py, int yoffs) {
        if (n != null) {
            Rectangle r = (Rectangle)this.nodeLocations.get(n);
            g.draw(r);
            Font ant = g.getFont();
            g.setFont(new Font("Serif", 0, 10));
            g.drawString("" + getData(n), r.x + 3, r.y + yoffs);
            if (px != Integer.MAX_VALUE) {
                g.drawLine(px, py, r.x + r.width / 2, r.y);
            }

            g.setFont(ant);
            this.drawTree(g, getLeft(n), r.x + r.width / 2, r.y + r.height, yoffs);
            this.drawTree(g, getRight(n), r.x + r.width / 2, r.y + r.height, yoffs);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.fm = g.getFontMetrics();
        Font ant = g.getFont();
        g.setFont(new Font("Monospaced", 1, 12));
        g.drawString(String.format("Size          = %d", this.tree.size()), 10, 44);
        int aO = this.optimalHeight(this.tree.size());
        int aOb = aO > 0 ? aO - 1 : 0;
        int aR = height(getRoot(this.tree));
        g.drawString(String.format("Optimal height = %d", aOb), 10, 60);
        if (aOb != aR) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN.darker());
        }

        g.drawString(String.format("Actual height  = %d", aR), 10, 76);
        g.setColor(Color.BLACK);
        g.setFont(ant);
        if (this.dirty) {
            this.calculateLocations();
            this.dirty = false;
        }

        Graphics2D g2d = (Graphics2D)g;
        RenderingHints rh = g2d.getRenderingHints();
        rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        g2d.translate(this.getWidth() / 2, this.parent2child);
        this.drawTree(g2d, getRoot(this.tree), Integer.MAX_VALUE, Integer.MAX_VALUE, this.fm.getLeading() + this.fm.getAscent());
        this.fm = null;
    }

    public static void main(String[] args) {
        BSTVisualizer app = new BSTVisualizer();
        app.setDefaultCloseOperation(3);
    }

    private int optimalHeight(int t) {
        return t == 0 ? 0 : (int)Math.ceil(Math.log((double)(t + 1)) / Math.log(2.0));
    }
}
