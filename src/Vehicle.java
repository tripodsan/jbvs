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

import geom.Circle;
import geom.Point;
import geom.Vector;
import geom.Line;
import geom.Constants;
import geom.Text;
import geom.Rectangle;
import geom.Shape;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.util.Iterator;

public class Vehicle extends Item {

    // shape of the vehicle
    private Circle c;

    // left motor speed
    private double v1 = 2;

    // right motor speed
    private double v2 = -2;

    // orientation line
    private Line directionLine;

    // motor line
    private Line motorLine;

    // name of vehicle
    private Text name;
    private ProximitySensor s1;

    private ProximitySensor s2;

    private CollisionSensor c0;
    private CollisionSensor c1;
    private CollisionSensor c2;

    private boolean collision;

    private boolean blocked = false;

    public Vehicle(String name, Item parent, Point p, double a, double r) {
        super(parent);
        this.c = new Circle(parent.getShape(), new Vector(p, a), r);
        this.name = new Text(c, new Point(-r/2, 4), name);
        directionLine = new Line(c, new Point(0, 0), 0, r);
        motorLine = new Line(c, new Point(0, -r), Constants.ROT90, 2*r);

        // left proximity sensor
        add(s1 = new ProximitySensor(this, new Vector(0, 0, Math.toRadians(-45)), 0, 250));

        // right proximity sensor
        add(s2 = new ProximitySensor(this, new Vector(0, 0, Math.toRadians(45)), 0, 250));

        // front collision sensor
        add(c0 = new CollisionSensor(this, r+1, 0, 0, 4, 20));

        // left collision sensor
        add(c1 = new CollisionSensor(this, 0, -r+1, Constants.ROT90, 4, 20));

        // right collision sensor
        add(c2 = new CollisionSensor(this, 0, r+1, Constants.ROT90, 4, 20));
    }

    Shape getShape() {
        return c;
    }

    public Circle getCircle() {
        return c;
    }

    public void draw(Graphics2D g) {
        super.draw(g);

        if (collision) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }
        // draw circle
        Stroke s = g.getStroke();
        if (isSelected()) {
            g.setStroke(new BasicStroke(2));
        }

        c.draw(g);
        // draw orientation line
        g.setColor(Color.BLACK);
        directionLine.draw(g);
        motorLine.draw(g);
        name.draw(g);

        g.setStroke(s);
    }

    public void prepare() {
        super.prepare();
    }

    public void calculate() {
        super.calculate();

        // now set the speed for the next tick
        v2 = 0.5 + s1.output() * s1.output() * 2;
        v1 = 0.5 + s2.output() * s2.output() * 2;
        v1 -= c0.output() * 3;
        v2 -= c0.output() * 3;
        v1 += c1.output() * 2;
        v2 += c2.output() * 2;
    }

    public void move() {
        if (!blocked) {
            double x = c.getDirection().x; // remember old position
            double y = c.getDirection().y;
            double ap = Math.PI / 2 + c.getOrientation();
            double a1 = Math.atan2(v1, c.getRadius());
            double a2 = Math.atan2(v2, c.getRadius());
            double x1 = x - Math.cos(ap+a1) * c.getRadius();
            double y1 = y - Math.sin(ap+a1) * c.getRadius();
            double x2 = x + Math.cos(ap-a2) * c.getRadius();
            double y2 = y + Math.sin(ap-a2) * c.getRadius();
            c.setDirection(new Vector((x1 + x2)/2, (y1 + y2)/2, c.getOrientation() + (a1-a2)/2));
            checkCollision();
            if (collision) {
                c.setDirection(new Vector(x, y, c.getOrientation() + (a1-a2)/2));
            }
        }
        checkCollision();
    }

    public double intersect(Line line) {
        return c.intersect(line);
    }

    public void checkCollision() {
        // calculate collision with all other items
        Iterator iter = getRootItem().getItems().iterator();
        collision = false;
        while (iter.hasNext()) {
            Item item = (Item) iter.next();
            if (item != this) {
                if (item.collides(this)) {
                    collision = true;
                }
            }
        }
    }

    public boolean collides(Vehicle v) {
        return c.intersects(v.c);
    }

    public boolean collides(Rectangle r) {
        Vector v = r.getGlobalDirection();
        v = c.getTransformation().transform(v, true);
        return c.contains(v.p) || r.intersects(c);
    }

    public Item selectItem(Point p) {
        Item i = super.selectItem(p);
        if (i == null) {
            if (parent != null) {
                p = parent.getShape().transform(new Vector(p,0), true).p;
            }
            if (c.contains(p)) {
                i = this;
            }
        }
        return i;
    }

    public String toString() {
        return "Vehicle " + name;
    }
}
