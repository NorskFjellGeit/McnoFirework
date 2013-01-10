package no.mcno.firework;

import java.io.PrintStream;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class McnoFirework extends JavaPlugin
{
  public int numfireworks = 200;
  public int fnum = 0;

  public int threadid = 0;

  public McnoFirework plugin = this;

  public void onDisable()
  {
    System.out.println("Fireworks System is disabled!");
  }

  public void onEnable()
  {
    System.out.println("Fireworks System is enabled!");
  }

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    Player player = null;

    if ((sender instanceof Player)) {
      player = (Player)sender;
    }
    if (player == null)
    {
      return true;
    }

    if (command.getName().equalsIgnoreCase("firework"))
    {
      this.fnum = 0;

      Location loc = player.getLocation();

      if (this.threadid != 0) {
        this.plugin.getServer().getScheduler().cancelTask(this.threadid);
        player.sendMessage("Stoppa Fyrverkeriet!");
        this.threadid = 0;
      } else {
        startShow(loc);
        player.sendMessage("Fyrer laus!");
      }

    }

    return true;
  }

  public void startShow(final Location loc)
  {
    this.threadid = getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
    {
      public void run()
      {
        McnoFirework.this.fnum += 1;

        Random rand = new Random();

        Location loc2 = loc.clone().add(rand.nextInt(5), 0.0D, rand.nextInt(5)).subtract(rand.nextInt(5), 0.0D, rand.nextInt(5));
        McnoFirework.this.startFireworks(loc2);
      }
    }
    , 10L, 5L);
  }

  public void startFireworks(Location loc)
  {
    Random rand = new Random(System.currentTimeMillis());

    Entity firework = loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

    Firework fw = (Firework)firework;
    FireworkMeta meta = fw.getFireworkMeta();

    FireworkEffect.Builder builder = FireworkEffect.builder();
    switch (rand.nextInt(7)) {
    case 0:
      builder.withColor(Color.AQUA);
      break;
    case 1:
      builder.withColor(Color.RED);
      break;
    case 2:
      builder.withColor(Color.GREEN);
      break;
    case 3:
      builder.withColor(Color.LIME);
      break;
    case 4:
      builder.withColor(Color.YELLOW);
      break;
    case 5:
      builder.withColor(Color.SILVER);
      break;
    case 6:
      builder.withColor(Color.FUCHSIA);
    }

    switch (rand.nextInt(5)) {
    case 0:
      builder.with(FireworkEffect.Type.BALL);
      break;
    case 1:
      builder.with(FireworkEffect.Type.BALL_LARGE);
      break;
    case 2:
      builder.with(FireworkEffect.Type.BURST);
      break;
    case 3:
      builder.with(FireworkEffect.Type.STAR);
      break;
    case 4:
      builder.with(FireworkEffect.Type.CREEPER);
    }

    switch (rand.nextInt(3)) {
    case 0:
      meta.setPower(1);
      break;
    case 1:
      meta.setPower(2);
      break;
    case 2:
      meta.setPower(3);
    }

    switch (rand.nextInt(3)) {
    case 0:
      builder.withFlicker();
      break;
    case 1:
      builder.withTrail();
      break;
    case 2:
    }

    meta.addEffect(builder.build());

    fw.setFireworkMeta(meta);
  }
}