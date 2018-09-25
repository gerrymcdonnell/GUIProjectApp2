/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//gerry mc donnell
//aidan fang
package app;

import utils.LookandFeelListener;
import utils.FileUtils;
import utils.ImageUtils;

import java.awt.image.*;
import java.awt.color.*;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.RescaleOp;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.OceanTheme;
import javax.swing.text.Style;
import utils.GeneralMethods;

public class ImageApp extends JFrame implements ActionListener, ChangeListener {

    private BufferedImage bImg;
    //TESTING img effect buttons
    //***************************************************************************
    private JPanel m_EffectPanel = new JPanel();
    //menus
    //***************************************************************************
    ///File Menu
    private JMenuBar m_menuBar = new JMenuBar();
    private JMenu m_mainMenu = new JMenu("File Menu");
    private JMenuItem m_openFile = new JMenuItem("Load Image");
    private JMenuItem m_saveImage = new JMenuItem("Save Image");
    //Effect Menu
    //***************************************************************************
    private JMenu m_effectMenu = new JMenu("Image Effects");
    private JMenuItem m_effectMenuHorizontalFlip = new JMenuItem("Horizontal Flip");
    private JMenuItem m_effectMenuVerticalFlip = new JMenuItem("Vertical Flip");
    private JMenuItem m_effectMenuBlur = new JMenuItem("Blur Image");
    private JMenuItem m_effectMenuSharpen = new JMenuItem("Sharpen Image");
    private JMenuItem m_effectMenuColourFilter = new JMenuItem("Colour Filter Image");
    private JMenuItem m_effectMenuInvert = new JMenuItem("Invert Image");
    private JMenuItem m_effectMenuResize = new JMenuItem("Resize Image");
    private JMenuItem m_effectMenuRotate = new JMenuItem("Rotate Image");
    private JMenuItem m_effectMenuSetColourTransparent = new JMenuItem("Set Transparent Colour");
    //***************************************************************************
    //Look and Feel Menu
    private JMenu m_lookandfeelMenu = new JMenu("Look and Feel");
    private JMenuItem m_lookandfeelMenuMetal = new JMenuItem("Metal");
    private JMenuItem m_lookandfeelMenuMotif = new JMenuItem("Motif");
    private JMenuItem m_lookandfeelMenuWindows = new JMenuItem("Windows");
    //slider panel
    private JPanel m_sliderPanel = new JPanel();
    private JSlider m_alphaslider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JSlider m_RedSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JSlider m_GreenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    private JSlider m_BlueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
    //Colour Panel
    //***************************************************************************
    private JPanel m_colourPanel = new JPanel();
    private JLabel m_coloursCaption = new JLabel("Colours:");
    private JButton m_red = new JButton("Red");
    private JButton m_green = new JButton("Green");
    private JButton m_blue = new JButton("Blue");
    private JButton m_black = new JButton("Black");
    private JButton m_purple = new JButton("Purple");
    private JButton m_navy = new JButton("Navy");
    private JButton m_teal = new JButton("Teal");
    private JButton m_fuchsia = new JButton("Fuchsia");
    private JButton m_grey = new JButton("Grey");
    private JButton m_lime = new JButton("Lime");
    private JButton m_yellow = new JButton("Yellow");
    private JButton m_pickDrawingColour = new JButton("Custom Colour");
    private JLabel m_linesizeCaption = new JLabel("Line Width");
    //slider
    private JSlider m_lineSize = new JSlider(JSlider.HORIZONTAL, 0, 15, 0);
    //***************************************************************************
    //Jinternal Frames
    //***************************************************************************
    private JLayeredPane desktop;
    private JInternalFrame internalFrame;
    Vector vectorInternalFrames = new Vector();
    Vector vectorInternalFramesCanvas = new Vector();
    //***************************************************************************
    private JPanel m_mainPanel = new JPanel();
    //default image to load
    private String m_currentImage = "";

    public ImageApp() {
        super("GUI Project Image Application");
        setSize(1024, 768);
        setLayout(new BorderLayout());

        //***************************************************************************
        //Colour Buttons
        setupLineSizeSlider();
        m_colourPanel.setLayout(new GridLayout(16, 1));
        m_colourPanel.add(m_coloursCaption);
        addButtonToPanel(m_colourPanel, m_red);
        addButtonToPanel(m_colourPanel, m_green);
        addButtonToPanel(m_colourPanel, m_blue);
        addButtonToPanel(m_colourPanel, m_black);
        addButtonToPanel(m_colourPanel, m_teal);
        addButtonToPanel(m_colourPanel, m_purple);
        addButtonToPanel(m_colourPanel, m_navy);
        addButtonToPanel(m_colourPanel, m_fuchsia);
        addButtonToPanel(m_colourPanel, m_grey);
        addButtonToPanel(m_colourPanel, m_lime);
        addButtonToPanel(m_colourPanel, m_yellow);
        addButtonToPanel(m_colourPanel, m_pickDrawingColour);
        m_colourPanel.add(m_linesizeCaption);
        m_colourPanel.add(m_lineSize);

        //slider
        //***************************************************************************
        m_sliderPanel.setLayout(new GridLayout(0, 1));
        setupRBGSliders(m_RedSlider);
        setupRBGSliders(m_GreenSlider);
        setupRBGSliders(m_BlueSlider);
        setupRBGSliders(m_alphaslider);

        //***************************************************************************
        //canvas
        add("Center", m_mainPanel);
        //slider panel
        add("South", m_sliderPanel);

        add("East", m_colourPanel);

        //File menu
        //***************************************************************************
        m_menuBar.add(m_mainMenu);
        setJMenuBar(m_menuBar);
        addMenuItem(m_mainMenu, m_openFile);
        addMenuItem(m_mainMenu, m_saveImage);

        //***************************************************************************
        //effect menu
        //***************************************************************************
        m_menuBar.add(m_effectMenu);
        addMenuItem(m_effectMenu, m_effectMenuHorizontalFlip);
        addMenuItem(m_effectMenu, m_effectMenuVerticalFlip);
        addMenuItem(m_effectMenu, m_effectMenuBlur);
        addMenuItem(m_effectMenu, m_effectMenuSharpen);
        addMenuItem(m_effectMenu, m_effectMenuColourFilter);
        addMenuItem(m_effectMenu, m_effectMenuInvert);
        addMenuItem(m_effectMenu, m_effectMenuResize);
        addMenuItem(m_effectMenu, m_effectMenuRotate);
        addMenuItem(m_effectMenu, m_effectMenuSetColourTransparent);
        //***************************************************************************

        //***************************************************************************
        //Look and Feel Menu
        //***************************************************************************
        m_menuBar.add(m_lookandfeelMenu);

        m_lookandfeelMenu.add(m_lookandfeelMenuMetal);
        m_lookandfeelMenu.add(m_lookandfeelMenuMotif);
        m_lookandfeelMenu.add(m_lookandfeelMenuWindows);

        LookandFeelListener m_lookandFeel = new LookandFeelListener(this);
        m_lookandfeelMenuMetal.addActionListener(m_lookandFeel);
        m_lookandfeelMenuMotif.addActionListener(m_lookandFeel);
        m_lookandfeelMenuWindows.addActionListener(m_lookandFeel);
        //***************************************************************************
        //end of menus

        ///////////////////////////////////////////////////////////
        //when app closes
        ///////////////////////////////////////////////////////////
        this.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        ///////////////////////////////////////////////////////////

        //Internal frames
        ///////////////////////////////////////////////////////////
        desktop = new JDesktopPane();
        desktop.setOpaque(true);
        add(desktop, BorderLayout.CENTER);
        ///////////////////////////////////////////////////////////
    }

    //Main method
    public static void main(String[] args) {
        ImageApp applicationDemo = new ImageApp();
        applicationDemo.show();
    }

    //method to handle actions
    public void doAction(Object source, MyCanvas m_FrameCanvas) {
        //rotate
        if (source == m_effectMenuRotate) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.rotate(bImg, 50);
            m_FrameCanvas.setImage(bImg);
        }

        //resize
        if (source == m_effectMenuResize) {
            bImg = m_FrameCanvas.getbImage();
            String w = GeneralMethods.sInputBox("Enter Width", "Image Resize");
            String h = GeneralMethods.sInputBox("Enter Heigth", "Image Resize");

            int iW = GeneralMethods.StringToInt(w);
            int iH = GeneralMethods.StringToInt(h);

            if (iW != -1 && iH != -1) {
                bImg = ImageUtils.resize(bImg, iW, iH);
                m_FrameCanvas.setImage(bImg);
                JInternalFrame f = getCurrentFrame();
                f.setTitle("File: " + m_currentImage + " Image Size (Width:" + bImg.getWidth() + " x Height: " + bImg.getHeight() + ")");
                f.setSize(iH, iH);
            }
        }
        //transparent color
        if (source == m_effectMenuSetColourTransparent) {
            bImg = m_FrameCanvas.getbImage();
            Color c = FileUtils.ShowColorDialog();
            bImg = ImageUtils.makeColorTransparent(bImg, c);
            m_FrameCanvas.setImage(bImg);
        }

        //save image
        if (source == m_saveImage) {
            bImg = m_FrameCanvas.getbImage();
            String sFile = FileUtils.saveFileDialog();
            if (FileUtils.bisFileAnImage(sFile) == true) {
                ImageUtils.saveImage(bImg, sFile);
            } else {
                GeneralMethods.showMsgBox("Error: Please enter a Valid File Name.");
            }
        }

        //load image
        //open file menu dialog
        if (source == m_openFile) {
            String sFile = FileUtils.openFileDialog(true);
            if (FileUtils.bisFileAnImage(sFile) == true) {
                loadImageinFrame(sFile);
            }
        }

        //allow user to pick drawing color
        if (source == m_pickDrawingColour) {
            Color m_newColor = FileUtils.ShowColorDialog();
            m_FrameCanvas.setColorToDraw(m_newColor);
        }

        //////////////////////////////////////////////////////////////////////////////
        //Effect Menu
        //horizon flip
        if (source == m_effectMenuHorizontalFlip) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.horizontalflip(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        if (source == m_effectMenuVerticalFlip) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.verticalflip(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        if (source == m_effectMenuBlur) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.blurImage(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        if (source == m_effectMenuSharpen) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.sharpenImage(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        if (source == m_effectMenuColourFilter) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.colourFilter(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        if (source == m_effectMenuInvert) {
            bImg = m_FrameCanvas.getbImage();
            bImg = ImageUtils.invertImage(bImg);
            m_FrameCanvas.setImage(bImg);
        }

        //colour buttons
        if (source == m_red) {
            m_FrameCanvas.setColorToDraw(Color.red);
        }
        if (source == m_green) {
            m_FrameCanvas.setColorToDraw(Color.GREEN);
        }
        if (source == m_blue) {
            m_FrameCanvas.setColorToDraw(Color.BLUE);
        }
        if (source == m_black) {
            m_FrameCanvas.setColorToDraw(Color.BLACK);
        }
        if (source == m_purple) {
            m_FrameCanvas.setColorToDraw(new Color(128, 0, 128));
        }
        if (source == m_navy) {
            m_FrameCanvas.setColorToDraw(new Color(0, 0, 128));
        }
        if (source == m_teal) {
            m_FrameCanvas.setColorToDraw(new Color(0, 128, 128));
        }
        if (source == m_yellow) {
            m_FrameCanvas.setColorToDraw(Color.YELLOW);
        }
        if (source == m_grey) {
            m_FrameCanvas.setColorToDraw(Color.gray);
        }
        if (source == m_lime) {
            m_FrameCanvas.setColorToDraw(new Color(0, 255, 0));
        }
        if (source == m_fuchsia) {
            m_FrameCanvas.setColorToDraw(new Color(255, 0, 255));
        }


    }

    //get the current selected frame/image and gets its canvas
    public void actionPerformed(ActionEvent e) {
        JInternalFrame m_internalFrame = getCurrentFrame();
        Object source = e.getSource();
        MyCanvas IFrameCanvas = getCurrentCanvas();
        //do an image effect
        doAction(source, IFrameCanvas);
    }

    //slider method
    //if (!source.getValueIsAdjusting()) {
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int n = (int) source.getValue();

        MyCanvas IFrameCanvas = getCurrentCanvas();
        if (IFrameCanvas != null) {
            if (source == m_RedSlider) {
                IFrameCanvas.adjustRGB(n, -1, -1, -1);
            }
            if (source == m_GreenSlider) {
                IFrameCanvas.adjustRGB(-1, n, -1, -1);
            }
            if (source == m_BlueSlider) {
                IFrameCanvas.adjustRGB(-1, -1, n, -1);
            }
            if (source == m_alphaslider) {
                IFrameCanvas.adjustRGB(-1, -1, -1, n);
            }
            repaint();
        }
        if (source == m_lineSize) {
            IFrameCanvas.setLineThickNess(n);
        }
    }

    public class MyCanvas extends JPanel implements MouseMotionListener, MouseListener {

        Graphics2D gTextObject;//draw test with this
        BufferedImage bImg;    //img currently displayed
        int pixelColor, alpha, red, green, blue;
        //////////////////////////////////////////////////////////////
        private Image m_canvas = null;
        Graphics2D canvasG = null;
        private Color m_colourToDraw = Color.green;
        private int m_oldX = 0,
                m_oldY = 0,
                m_x = 0,
                m_y = 0;
        private int m_lineThickness = 6;
        //////////////////////////////////////////////////////////////

        public MyCanvas() {
            super();
            //////////////////////////////////////////////////////////////
            addMouseMotionListener(this);
            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    m_oldX = m_x = e.getX();
                    m_oldY = m_y = e.getY();

                    //double click on image to add text
                    //////////////////////////////////////////////////////////////
                    if (e.getClickCount() == 2) {
                        String s = GeneralMethods.sInputBox("Enter Text", "Enter Text To add to Image");
                        drawText(s, e.getX(), e.getY());
                    }
                    //////////////////////////////////////////////////////////////
                }
            });
            //////////////////////////////////////////////////////////////
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            //draw the buffered image File
            //////////////////////////////////////////////////////////////
            g.drawImage(bImg, 0, 0, getWidth(), getHeight(), this);
            /////////////////////////////////////////////////////////////

            //drawing with mouse
            /////////////////////////////////////////////////////////////
            if (canvasG == null) {
                canvasG = bImg.createGraphics();
            }

            canvasG.setColor(m_colourToDraw);
            canvasG.drawLine(m_oldX, m_oldY, m_x, m_y);

            //line thickness
            canvasG.setStroke(new BasicStroke(m_lineThickness));

            // draw the canvas onto the panel
            g.drawImage(m_canvas, 0, 0, this);
            m_oldX = m_x;
            m_oldY = m_y;
            /////////////////////////////////////////////////////////////

        }

        public void clearDrawingArea() {
            canvasG = null;
        }

        public void setImage(BufferedImage imageToDraw) {
            bImg = imageToDraw;
            repaint();
        }

        public void setColorToDraw(Color c) {
            m_colourToDraw = c;
        }

        public void setLineThickNess(int i) {
            m_lineThickness = i;
        }

        public BufferedImage getbImage() {
            return bImg;
        }

        public void drawText(String s, int x, int y) {
            int m_Y, m_X;

            if (gTextObject == null) {
                gTextObject = bImg.createGraphics();
            }

            if (s != null) {
                gTextObject.setColor(m_colourToDraw);
                gTextObject.setFont(new Font("Arial", Font.BOLD, 80));
                gTextObject.drawString(s, x, y);
                repaint();
            }
        }


        public void adjustRGB(int newRed, int newGreen, int newBlue, int newAlpha) {
            for (int y = 0; y < bImg.getHeight(); y++) {
                for (int x = 0; x < bImg.getWidth(); x++) {
                    pixelColor = bImg.getRGB(x, y);
                    alpha = (pixelColor >>> 24) & 0xff;
                    red = (pixelColor >>> 16) & 0xff;
                    green = (pixelColor >>> 8) & 0xff;
                    blue = pixelColor & 0xff;

                    if (newRed != -1) {
                        bImg.setRGB(x, y, (new Color(newRed, green, blue)).getRGB());
                    } else if (newGreen != -1) {
                        bImg.setRGB(x, y, (new Color(red, newGreen, blue, alpha)).getRGB());
                    } else if (newBlue != -1) {
                        bImg.setRGB(x, y, (new Color(red, green, newBlue, alpha)).getRGB());
                    } else if (newAlpha != -1) {
                        bImg.setRGB(x, y, (new Color(red, green, blue, newAlpha)).getRGB());
                    }
                }
            }
        }

        //abstract methods
        public void mouseMoved(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            m_x = e.getX();
            m_y = e.getY();
            repaint();
        }

        public void mouseClicked(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseReleased(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseEntered(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void mouseExited(MouseEvent me) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        ///////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////
    }//end of canvas

    //****************************************************************************************
    //internal FRames
    //****************************************************************************************
    public void loadImageinFrame(String sFile) {
        try {
            BufferedImage bTemp = ImageUtils.loadImage(sFile);
            MyCanvas m_Canvas = new MyCanvas();

            //Store current image path here
            m_currentImage = sFile;

            boolean resizable = true;
            boolean closeable = true;
            boolean maximizable = true;
            boolean iconifiable = true;
            int w, h;

            //get the width and height of loaded image
            w = bTemp.getWidth();
            h = bTemp.getHeight();

            //create a new internal frame for image
            internalFrame = new JInternalFrame("Filename: " + sFile + " Image Size (Width:" + w + " x Height: " + h + ")", resizable, closeable, maximizable, iconifiable);

            //set the bounds of the internal frame
            internalFrame.setBounds(2, 2, w, h);

            //draw the image on the internal frames canvas
            m_Canvas.setImage(bTemp);

            internalFrame.getContentPane().add(m_Canvas);
            internalFrame.setVisible(true);
            desktop.add(internalFrame, new Integer(1));
            internalFrame.moveToFront();

            //add internal frame to vector
            vectorInternalFrames.addElement(internalFrame);
            //add canvas to vector
            vectorInternalFramesCanvas.addElement(m_Canvas);

        } catch (Exception IIOException) {
            GeneralMethods.showMsgBox("Error: Unable to Load Image:\n " + sFile);
        }
    }

    //get the current selected frame
    public JInternalFrame getCurrentFrame() {
        for (int i = 0; i < vectorInternalFrames.size(); i++) {
            JInternalFrame currentFrame = (JInternalFrame) vectorInternalFrames.elementAt(i);
            if (currentFrame.isSelected()) {
                return currentFrame;
            }
        }
        return null;
    }

    //get the canvas object of the selected frame
    public MyCanvas getCurrentCanvas() {
        for (int i = 0; i < vectorInternalFrames.size(); i++) {
            JInternalFrame currentFrame = (JInternalFrame) vectorInternalFrames.elementAt(i);
            if (currentFrame.isSelected()) {
                return (MyCanvas) vectorInternalFramesCanvas.elementAt(i);
            }
        }
        return null;
    }
    //****************************************************************************************

    //****************************************************************************************
    //add a menu item to a parent menu and adds an action listener
    //****************************************************************************************
    public void addMenuItem(JMenu m_parentMenu, JMenuItem m_subMenuItem) {
        //add menu item to the parent menu
        m_parentMenu.add(m_subMenuItem);
        //add the action listner
        m_subMenuItem.addActionListener(this);
    }

    public void addButtonToPanel(JPanel m_panel, JButton m_button) {
        //add button to panel
        m_panel.add(m_button);
        //add action listner for button
        m_button.addActionListener(this);
    }

    //setup the 4 sliders as follows
    public void setupRBGSliders(JSlider mySlider) {
        mySlider.setMajorTickSpacing(25);
        mySlider.setMinorTickSpacing(1);
        mySlider.setPaintTicks(true);
        mySlider.setPaintLabels(true);
        //mySlider.setPreferredSize(new Dimension(400, 20));

        //addChangeListener
        mySlider.addChangeListener(this);
        //add to the sliderpanel
        m_sliderPanel.add(mySlider);
    }

    //setup the slider for line size when drawing
    public void setupLineSizeSlider() {
        //setup line size slider
        m_lineSize.addChangeListener(this);
        m_lineSize.setMinorTickSpacing(1);
        m_lineSize.setMajorTickSpacing(2);
        m_lineSize.setPaintTicks(true);
        m_lineSize.setPreferredSize(new Dimension(100, 15));
    }
}
