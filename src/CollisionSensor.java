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

import geom.Rectangle;
import geom.Shape;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Iterator;

public class CollisionSensor extends Item {

    Rectangle rect;

    boolean collides;

    public CollisionSensor(Item parent, double x, double y, double a, double w, double h) {
        super(parent);
        rect = new Rectangle(parent.getShape(), x, y, a, w, h);
    }

    public void draw(Graphics2D g) {
        if (collides) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.GREEN);
        }
        rect.draw(g);
    }

    public void calculate() {
        collides = false;
        // calculate intersection with all other items
        Iterator iter = getRootItem().getItems().iterator();
        while (iter.hasNext()) {
            Item item = (Item) iter.next();
            // if item is parent, or items parent is parent, ignore
            if (item != parent && item.parent != parent) {
                if (item.collides(rect)) {
                    collides = true;
                }
            }
        }
    }

    public double output() {
        return collides ? 1 : 0;
    }

    Shape getShape() {
        return rect;
    }
}
