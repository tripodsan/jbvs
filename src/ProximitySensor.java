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

import geom.Line;
import geom.Vector;
import geom.Shape;

import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.Color;

public class ProximitySensor extends Item {

    private Line rangeLine;

    private Line measureLine;

    double measure = Double.MAX_VALUE;

    public ProximitySensor(Item parent, Vector v, double vow, double range) {
        super(parent);
        this.rangeLine = new Line(parent.getShape(), v, range);
        this.measureLine = new Line(parent.getShape(), v, 0);
    }

    public synchronized void calculate() {
        measure = measure();
        if (measure <= rangeLine.getLength()) {
            measureLine.setLength(measure);
        } else {
            measureLine.setLength(0);
        }
    }

    public synchronized void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        rangeLine.draw(g);
        g.setColor(Color.RED);
        measureLine.draw(g);
    }

    public double measure() {
        double best = Double.MAX_VALUE;
        // calculate intersection with all other items
        Iterator iter = getRootItem().getItems().iterator();
        while (iter.hasNext()) {
            Item item = (Item) iter.next();
            // if item is parent, or items parent is parent, ignore
            if (item != parent && item.parent != parent) {
                double d = item.intersect(rangeLine);
                if (d<best) {
                    best = d;
                }
            }
        }
        return best;
    }

    public double output() {
        // simple linear output
        if (measure < rangeLine.getLength()) {
            return measure / rangeLine.getLength();
        } else {
            return 1;
        }
    }

    Shape getShape() {
        return rangeLine;
    }
}
