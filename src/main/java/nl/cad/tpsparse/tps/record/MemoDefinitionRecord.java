/*
 *  Copyright 2012-2013 E.Hooijmeijer
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package nl.cad.tpsparse.tps.record;

import nl.cad.tpsparse.bin.RandomAccess;

public class MemoDefinitionRecord {

    private String externalFile;
    private String name;
    private int length;
    private int flags;

    public MemoDefinitionRecord(RandomAccess rx) {
        externalFile = rx.zeroTerminatedString();
        if (externalFile.length() == 0) {
            if (rx.leByte() != 1) {
                throw new IllegalArgumentException("Bad Memo Definition, missing 0x01 after zero string.");
            }
        }
        name = rx.zeroTerminatedString();
        length = rx.leUShort();
        flags = rx.leUShort();
    }

    public String getName() {
        return name;
    }

    public String getNameNoTable() {
        int idx = name.indexOf(':');
        if (idx >= 0) {
            return name.substring(idx + 1);
        }
        return name;
    }

    public int getFlags() {
        return flags;
    }

    public boolean isMemo() {
        return (flags & 0x04) == 0;
    }

    public boolean isBlob() {
        return (flags & 0x04) != 0;
    }

    @Override
    public String toString() {
        return "MemoDefinition(" + externalFile + "," + name + "," + length + "," + flags + ")";
    }

}
