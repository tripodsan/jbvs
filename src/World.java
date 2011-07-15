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

import geom.Point;
import geom.Vector;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class World extends JPanel implements MouseListener, MouseMotionListener {

    private Obstacle root;

    private Item selectedItem;

    private Point selectStart;

    private Vector selectedItemPosition;

    private int selectButton;

    private long tickTime;

    private BBSimulator simulator;

    public World(BBSimulator simulator) {
        this.simulator = simulator;
        this.setSize(new Dimension(800, 800));
        this.setPreferredSize(new Dimension(800, 800));
        this.setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);

        root = new Obstacle(null, 400, 400, 0, 780, 780);
        root.add(new Obstacle(root, -290, 0, 0, 200, 50));

        root.setReverse(true);

        Item i = new Obstacle(root, 100, 50, 1, 100, 50);
        root.add(i);
        Item j = new Obstacle(i, 100, 50, 1, 100, 50);
        i.add(j);
        Item k = new Obstacle(j, 100, 50, 1, 100, 50);
        j.add(k);

        root.add(new Vehicle("0", root, new Point( 0, -100), Math.toRadians(10), 30));
        root.add(new Vehicle("1", root, new Point( 100, -100), Math.toRadians(20), 30));
        root.add(new Vehicle("2", root, new Point(-100, -100), Math.toRadians(30), 30));
        root.add(new Vehicle("3", root, new Point( 200, -100), Math.toRadians(40), 30));
        root.add(new Vehicle("4", root, new Point(-200, -100), Math.toRadians(50), 30));
        root.add(new Vehicle("5", root, new Point( 300, -100), Math.toRadians(60), 30));
        root.add(new Vehicle("6", root, new Point(-300, -100), Math.toRadians(70), 30));
        root.add(new Vehicle("7", root, new Point( 300, -200), Math.toRadians(80), 30));
        root.add(new Vehicle("8", root, new Point(-300, -200), Math.toRadians(90), 30));
        root.add(new Vehicle("9", root, new Point( 100, -200), Math.toRadians(20), 30));
        root.add(new Vehicle("10", root, new Point(-100, -200), Math.toRadians(30), 30));
    }

    public void tick() {
        long t1 = System.currentTimeMillis();
        root.prepare();
        root.move();
        root.calculate();
        tickTime = System.currentTimeMillis() - t1;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        long t1 = System.currentTimeMillis();
        root.draw(g2);
        t1 = System.currentTimeMillis() - t1;
        g2.drawString("tick: " + tickTime + "ms draw: " + t1 + "ms", 12, 22);
        /*
        if (selectedItem != null) {
            Vector p = selectedItem.getWorldVector();
            Vector q = selectedItem.getVector();
            g2.setColor(Color.BLACK);
            StringBuffer buf = new StringBuffer("abs: x=");
            buf.append(p.x);
            buf.append(" y=");
            buf.append(p.y);
            buf.append(" a=");
            buf.append(p.a);
            g2.drawString(buf.toString(), (int) p.x, (int) p.y);

            buf = new StringBuffer("rel: x=");
            buf.append(q.x);
            buf.append(" y=");
            buf.append(q.y);
            buf.append(" a=");
            buf.append(q.a);
            g2.drawString(buf.toString(), (int) p.x, (int) p.y + 15);
        }
        */
    }

    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent e) {
        selectStart = new Point(e.getX(), e.getY());
        Item s = root.selectItem(selectStart);
        if (s != selectedItem && selectedItem!=null) {
            selectedItem.setSelected(false);
        }
        selectedItem = s;
        simulator.itemSelected(s);
        if (selectedItem != null) {
            selectedItem.setSelected(true);
            selectedItemPosition = selectedItem.getShape().getGlobalDirection();
            selectButton = e.getButton();
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        if (selectedItem != null) {
            Point p = new Point(e.getX(), e.getY());
            if (selectButton == MouseEvent.BUTTON1) {
                Point d = p.translate(selectStart, true);
                d = d.translate(selectedItemPosition.p, false);
                selectedItem.getShape().setGlobalPosition(d);
            } else if (selectButton == MouseEvent.BUTTON3) {
                double a = selectedItem.getShape().getGlobalDirection().p.getAngle(p);
                selectedItem.getShape().setGlobalOrientation(a);
            }
        }
    }

    public void mouseMoved(MouseEvent e) {
        //
    }
}
