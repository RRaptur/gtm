package me.rapturr.gtm.utilities;

import de.tr7zw.nbtapi.NBTFile;
import de.tr7zw.nbtapi.plugin.NBTAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.io.File;
import java.io.IOException;

public class NBT {

    public void test(Entity entity) throws IOException {
        NBTFile file = new NBTFile(new File("test.nbt"));

    }
}
