package fr.rexey.posaver;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;


public class Main extends JavaPlugin implements Listener {

    private HashMap<String, IntPos> positions;

    @Override
    public void onEnable() {
        super.onEnable();
        positions = new HashMap<>();
        for (String key : this.getConfig().getKeys(false)) {
            positions.put(key, new IntPos(this.getConfig().getString(key)));
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        for (String s : positions.keySet()) {
            this.getConfig().set(s, positions.get(s).toString());
        }
        saveConfig();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player) sender;
        switch (label) {
            case "saveposof": return handleSavePosOf(p,args);
            case "saveposfromchat": return handlesaveposfromchat(p,args);
            case "showposof": return handleLoadPosOf(p,args);
            case "listpos": return handleListPos(p);
        }
        return true;
    }

    private boolean handlesaveposfromchat(Player sender, String[] args) {
        if( args.length != 4 ){
            sender.sendRawMessage("bad formatting : /saveposfromchat name x y z");
        } else if( positions.containsKey(args[0])) {
            sender.sendRawMessage("this position name already exist. be more imaginative !");
            return false;
        } else {
            IntPos tmp = new IntPos(Integer.valueOf(args[1]),Integer.valueOf(args[2]),Integer.valueOf(args[3]));
            positions.put(args[0],tmp);
            sender.sendRawMessage("the position " + tmp.displayString() + " has been saved as " + args[0]);
        }
        return true;
    }


    private boolean handleSavePosOf(Player sender, String[] args) {

        if (args.length == 0) {
            sender.sendRawMessage("you need to name the position");
            return false;
        } else if ( args.length > 1) {
            sender.sendRawMessage("name of the position must not contain spaces");
            return false;
        } else if( positions.containsKey(args[0])) {
            sender.sendRawMessage("this position name already exist. be more imaginative !");
            return false;
        } else {
            IntPos tmp = new IntPos( sender.getLocation());
            positions.put(args[0],tmp);
            sender.sendRawMessage("the position " + tmp.displayString() + " has been saved as " + args[0]);
        }
        return true;
    }

    private boolean handleLoadPosOf(Player sender,  String[] args) {
        if (args.length == 0) {
            sender.sendRawMessage("you need to choose the name of the position you want to show");
            return false;
        } else if ( args.length > 1) {
            sender.sendRawMessage("name of the position must not contain spaces");
            return false;
        } else if( !positions.containsKey(args[0])) {
            sender.sendRawMessage("this position does not exist");
        } else {
            sender.sendRawMessage(args[0] + " is at " + positions.get(args[0]).displayString() );
        }
        return true;
    }


    private boolean handleListPos(Player sender) {
        if( positions.size() == 0){
            sender.sendRawMessage("no positions saved yet");
        } else {
            positions.forEach((s, intPos) ->
                    sender.sendRawMessage(s + " is at " + intPos.displayString() )
            );
        }
        return true;
    }

}
