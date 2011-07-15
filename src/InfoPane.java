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
import java.awt.Color;
import java.awt.Dimension;

/**
 * This Class implements...
 *
 * @author tripod
 * @version $Revision:$, $Date:$
 */
public class InfoPane {

    private JPanel content;

    private Item selectedItem;

    private JLabel itemName;
    private JButton button1;

    public JPanel getContentPane() {
        return content;
    }

    public void setSelectedItem(Item s) {
        selectedItem = s;
        if (s != null) {
            itemName.setText(s.toString());
        } else {
            itemName.setText("");
        }
    }
}
