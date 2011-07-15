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
import geom.Line;
import geom.Rectangle;
import geom.Shape;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.awt.Graphics2D;

abstract public class Item {

    // the parent item
    final Item parent;

    private boolean selected;

    final LinkedList children = new LinkedList();

    public Item(Item parent) {
        this.parent = parent;
    }

    abstract Shape getShape();

    public void add(Item i) {
        children.add(i);
    }

    public void prepare() {
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            ((Item) iter.next()).prepare();
        }
    }

    public void move() {
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            ((Item) iter.next()).move();
        }
    }

    public void calculate() {
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            ((Item) iter.next()).calculate();
        }
    }

    public void draw(Graphics2D g) {
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            ((Item) iter.next()).draw(g);
        }
    }

    public Item getRootItem() {
        return parent == null ? this : parent.getRootItem();
    }

    public Set getItems() {
        HashSet set = new HashSet();
        set.add(this);
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            set.addAll(((Item) iter.next()).getItems());
        }
        return set;
    }

    public double intersect(Line v) {
        return Double.MAX_VALUE;
    }

    public boolean collides(Vehicle v) {
        return false;
    }

    public boolean collides(Rectangle r) {
        return false;
    }

    public Item selectItem(Point p) {
        Iterator iter = children.iterator();
        while (iter.hasNext()) {
            Item s = ((Item) iter.next()).selectItem(p);
            if (s != null) {
                return s;
            }
        }
        return null;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
