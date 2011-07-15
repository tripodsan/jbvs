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

import geom.*;
import geom.Point;
import geom.Rectangle;
import geom.Shape;

import java.awt.*;

public class Obstacle extends Item {

    Rectangle r;

    boolean reverse;

    boolean collides = false;

    public Obstacle(Item parent, double x, double y, double a, double w, double h) {
        super(parent);
        r = new Rectangle(parent == null ? null : parent.getShape(), x, y, a, w, h);
    }

    Shape getShape() {
        return r;
    }

    public void prepare() {
        super.prepare();
        // nothin to do
        collides = false;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    public void draw(Graphics2D g) {
        super.draw(g);
        if (collides) {
            g.setColor(Color.BLUE);
        } else {
            g.setColor(Color.BLACK);
        }
        Stroke s = g.getStroke();
        if (isSelected()) {
            g.setStroke(new BasicStroke(2));
        }
        r.draw(g);
        g.setStroke(s);
    }

    public Item selectItem(Point p) {
        Item i = super.selectItem(p);
        if (i == null) {
            if (parent != null) {
                p = parent.getShape().transform(new Vector(p,0), true).p;
            }
            if (r.contains(p) ^ reverse) {
                i = this;
            }
        }
        return i;
    }

    public double intersect(Line v) {
        return r.intersect(v);
    }

    public boolean collides(Rectangle rect) {
        Transformation pt = r.getTransformation();
        Vector v1 = pt.transform(rect.getTop().getGlobalDirection(), true);
        if (r.contains(v1.p) ^ reverse) {
            return true;
        }
        v1 = pt.transform(rect.getRight().getGlobalDirection(), true);
        if (r.contains(v1.p) ^ reverse) {
            return true;
        }
        v1 = pt.transform(rect.getBottom().getGlobalDirection(), true);
        if (r.contains(v1.p) ^ reverse) {
            return true;
        }
        v1 = pt.transform(rect.getLeft().getGlobalDirection(), true);
        return r.contains(v1.p) ^ reverse;
    }

    public boolean collides(Vehicle v) {
        if (r.intersects(v.getCircle())) {
            return collides = true;
        } else {
            return false;
        }
    }
}
