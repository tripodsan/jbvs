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
package geom;

import java.awt.Graphics2D;

public class Rectangle extends Shape implements Constants {

    // width of rectangle
    double w;

    // height of rectangle
    double h;

    Line top;
    Line left;
    Line bottom;
    Line right;

    public Rectangle(Transformation t, double x, double y, double a, double w, double h) {
        super(t, new Vector(x, y, a));
        this.w = w;
        this.h = h;
        calcLines();
    }

    private void calcLines() {
        top    = new Line(this, new Point(-w/2, -h/2), 0, w);
        right  = new Line(this, new Point( w/2, -h/2), ROT90, h);
        bottom = new Line(this, new Point( w/2,  h/2), ROT180, w);
        left   = new Line(this, new Point(-w/2,  h/2), ROT270, h);
    }

    public Line getTop() {
        return top;
    }

    public Line getLeft() {
        return left;
    }

    public Line getBottom() {
        return bottom;
    }

    public Line getRight() {
        return right;
    }

    public void draw(Graphics2D g) {
        top.draw(g);
        right.draw(g);
        bottom.draw(g);
        left.draw(g);
    }

    /**
     * checks if this rectangle contains the (local) point q
     *
     * @param q
     * @return
     */
    public boolean contains(Point q) {
        // translate back
        Vector c = getDirection();
        q = q.translate(c.p, true);
        q = q.rotate(-c.a);
        double x = Math.abs(q.x);
        double y = Math.abs(q.y);
        return x<=w/2 && y<=h/2;
    }

    public double intersect(Line line) {
        double t = top.intersect(line);
        t = Math.min(t, right.intersect(line));
        t = Math.min(t, bottom.intersect(line));
        return Math.min(t, left.intersect(line));
    }

    public boolean intersects(Circle c) {
        return top.intersects(c)
                || right.intersects(c)
                || bottom.intersects(c)
                || left.intersects(c);
    }

}
