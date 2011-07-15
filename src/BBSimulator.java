/*
 * Copyright 2011 Tobias Bocanegra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import javax.swing.*;
import java.awt.*;

/**
 * This Class implements...
 *
 * @author tripod
 * @version $Revision:$, $Date:$
 */
public class BBSimulator extends JFrame implements Runnable {

    private JPanel contentPane;

    private JPanel worldPane;

    private JPanel infoPane;

    private InfoPane info;

    private World world;

    private long lastTick = 0;

    private long tickTime = 20;

    public BBSimulator(String title) throws HeadlessException {
        super(title);
        world = new World(this);
        info = new InfoPane();
        worldPane.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        worldPane.add(world);
        infoPane.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        infoPane.add(info.getContentPane());
        contentPane.setOpaque(true); //content panes must be opaque
        setContentPane(contentPane);
    }

    public void itemSelected(Item i) {
        info.setSelectedItem(i);
    }

    public void run() {
        while (true) {
            long d = System.currentTimeMillis() - lastTick;
            if (d>tickTime) {
                lastTick = System.currentTimeMillis();
                world.tick();
            } else {
                try {
                    Thread.sleep(d);
                } catch (InterruptedException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        //JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        BBSimulator frame = new BBSimulator("Breitenberg Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        //frame.world = new World();

/*
        JMenu viewMenu = new JMenu("View");
        item = new JCheckBoxMenuItem("Highligth spans", true);
        item.addItemListener(sudoku);
        viewMenu.add(item);
        item = new JCheckBoxMenuItem("Show possible numbers", true);
        item.addItemListener(sudoku);
        viewMenu.add(item);
        menuBar.add(viewMenu);
*/

        //Create and set up the content pane.
//        frame.getContentPane().setLayout(new GridBagLayout());
  //      frame.getContentPane().add(frame.world);

        //Display the window.
        frame.pack();
        frame.setVisible(true);

        // start simulation
        new Thread(frame).start();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void setData(World data) {
    }

    public void getData(World data) {
    }

    public boolean isModified(World data) {
        return false;
    }
}
