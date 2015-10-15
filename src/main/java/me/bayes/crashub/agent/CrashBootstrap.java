package me.bayes.crashub.agent;

import org.crsh.plugin.Embedded;
import org.crsh.plugin.ServiceLoaderDiscovery;
import org.crsh.plugin.SimplePluginDiscovery;
import org.crsh.standalone.CRaSH;
import org.crsh.util.Utils;
import org.crsh.vfs.spi.FSMountFactory;
import org.crsh.vfs.spi.file.FileMountFactory;
import org.crsh.vfs.spi.url.ClassPathMountFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Created by Kevin Bayes on 2015/10/14.
 */
public class CrashBootstrap extends Embedded {

    private Map<String, FSMountFactory<?>> drivers = new HashMap<>(3);

    /** . */
    private String cmdMountPointConfig;

    /** . */
    private String confMountPointConfig;

    public void start() {

        Map<String,Object> attributes = Collections.unmodifiableMap(new HashMap<>());

        try {
            drivers.put("classpath", new ClassPathMountFactory(Thread.currentThread().getContextClassLoader()));
            drivers.put("file", new FileMountFactory(Utils.getCurrentDirectory()));
        }
        catch (Exception e) {
            log.log(Level.SEVERE, "Could not initialize classpath driver", e);
            return;
        }

        Properties properties = new Properties();
        properties.put("crash.ssh.port", "2000");
        properties.put("crash.ssh.auth_timeout", "300000");
        properties.put("crash.ssh.idle_timeout", "300000");

        properties.put("crash.auth", "simple");
        properties.put("crash.auth.simple.username", "admin");
        properties.put("crash.auth.simple.password", "admin");

        setConfig(properties);

        System.out.println("Starting CRaSH!!!!!");

        start(attributes, new ServiceLoaderDiscovery(Thread.currentThread().getContextClassLoader()), Thread.currentThread().getContextClassLoader());


    }

    @Override
    protected Map<String, FSMountFactory<?>> getMountFactories() {
        return drivers;
    }

    @Override
    protected String resolveConfMountPointConfig() {
        return confMountPointConfig != null ? confMountPointConfig : getDefaultConfMountPointConfig();
    }

    @Override
    protected String resolveCmdMountPointConfig() {
        return cmdMountPointConfig != null ? cmdMountPointConfig : getDefaultCmdMountPointConfig();
    }

    protected String getDefaultCmdMountPointConfig() {
        return "classpath:/crash/commands/";
    }

    protected String getDefaultConfMountPointConfig() {
        return "classpath:/crash/";
    }

}
