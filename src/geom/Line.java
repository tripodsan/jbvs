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

public class Line extends Shape {

    // length of line
    double l;

    public Line(Transformation t, Point p, double a, double l) {
        this(t, new Vector(p, a), l);
    }

    public Line(Transformation t, Vector v, double l) {
        super(t, v);
        this.l = l;
    }

    public double getLength() {
        return l;
    }

    public void setLength(double l) {
        this.l = l;
    }

    public void draw(Graphics2D g) {
        if (l != 0) {
            Vector p = getGlobalDirection();
            g.drawLine((int) p.x, (int) p.y, (int) (p.x + l*Math.cos(p.a)), (int) (p.y + l*Math.sin(p.a)));
        }
    }

    public double intersect(Line line) {
        Vector v1 = getGlobalDirection();
        Vector v2 = line.getGlobalDirection();

        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        double da = v2.a - v1.a;
        double t1 = (dy * Math.cos(v2.a) - dx * Math.sin(v2.a)) / Math.sin(da);
        double t2 = (dx * Math.sin(v1.a) - dy * Math.cos(v1.a)) / Math.sin(-da);
        return (t2<0 || t2>line.l || t1 <0 || t1>l) ? Double.MAX_VALUE : t2;
    }

    public boolean intersects(Circle c) {
        return c.intersect(this) < Double.MAX_VALUE;
    }

}
