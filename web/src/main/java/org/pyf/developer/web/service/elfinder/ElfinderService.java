package org.pyf.developer.web.service.elfinder;

import org.apache.commons.beanutils.BeanUtils;
import org.pyf.developer.elfinder.ElFinderConstants;
import org.pyf.developer.elfinder.core.Volume;
import org.pyf.developer.elfinder.core.VolumeSecurity;
import org.pyf.developer.elfinder.core.impl.DefaultVolumeSecurity;
import org.pyf.developer.elfinder.core.impl.SecurityConstraint;
import org.pyf.developer.elfinder.param.ElfinderConfiguration;
import org.pyf.developer.elfinder.param.Node;
import org.pyf.developer.elfinder.param.Thumbnail;
import org.pyf.developer.elfinder.service.ElfinderStorage;
import org.pyf.developer.elfinder.service.ElfinderStorageFactory;
import org.pyf.developer.elfinder.service.VolumeSources;
import org.pyf.developer.elfinder.service.impl.DefaultElfinderStorage;
import org.pyf.developer.elfinder.service.impl.DefaultElfinderStorageFactory;
import org.pyf.developer.elfinder.service.impl.DefaultThumbnailWidth;
import org.pyf.developer.elfinder.support.locale.LocaleUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ${DESCRIPTION}
 * Created by hanqf on 2018/9/21 18:15.
 */

@Service
public class ElfinderService {

    @Resource(name = "homeNode")
    private Node homeNode;

    @Resource(name = "shareNode")
    private Node shareNode;

    @Resource(name = "shareallNode")
    private Node shareallNode;

    public ElfinderStorageFactory getElfinderStorageFactory(String username,String flag) {
        DefaultElfinderStorageFactory elfinderStorageFactory = new DefaultElfinderStorageFactory();
        elfinderStorageFactory.setElfinderStorage(getElfinderStorage(username,flag));
        return elfinderStorageFactory;
    }

    private ElfinderConfiguration getElfinderConfiguration(String flag) throws InvocationTargetException, IllegalAccessException {
        ElfinderConfiguration elfinderConfiguration = new ElfinderConfiguration();
        elfinderConfiguration.setThumbnail(new Thumbnail());

        List<Node> list = new ArrayList<>();


        if("home".equals(flag)) {
            list.add(homeNode);
            Node homeNodeCopy = new Node();
            BeanUtils.copyProperties(homeNodeCopy, homeNode);
            homeNodeCopy.setAlias(homeNode.getAlias() + "(c)");
            list.add(homeNodeCopy);

            list.add(shareNode);
            Node shareNodeCopy = new Node();
            BeanUtils.copyProperties(shareNodeCopy, shareNode);
            shareNodeCopy.setAlias(shareNode.getAlias() + "(c)");
            list.add(shareNodeCopy);
        }else {


            list.add(shareallNode);
            Node shareNodeCopy = new Node();
            BeanUtils.copyProperties(shareNodeCopy, shareallNode);
            shareNodeCopy.setAlias(shareallNode.getAlias() + "(c)");
            list.add(shareNodeCopy);
        }


        elfinderConfiguration.setVolumes(list);

        return elfinderConfiguration;
    }

    private ElfinderStorage getElfinderStorage(String username,String flag) {

        DefaultElfinderStorage defaultElfinderStorage = new DefaultElfinderStorage();

        // creates thumbnail
        DefaultThumbnailWidth defaultThumbnailWidth = new DefaultThumbnailWidth();
        ElfinderConfiguration elfinderConfiguration = null;
        try {
            elfinderConfiguration = getElfinderConfiguration(flag);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        defaultThumbnailWidth.setThumbnailWidth(elfinderConfiguration.getThumbnail().getWidth().intValue());

        // creates volumes, volumeIds, volumeLocale and volumeSecurities
        Character defaultVolumeId = 'A';
        List<Node> elfinderConfigurationVolumes = elfinderConfiguration.getVolumes();
        List<Volume> elfinderVolumes = new ArrayList<>(elfinderConfigurationVolumes.size());
        Map<Volume, String> elfinderVolumeIds = new HashMap<>(elfinderConfigurationVolumes.size());
        Map<Volume, Locale> elfinderVolumeLocales = new HashMap<>(elfinderConfigurationVolumes.size());
        List<VolumeSecurity> elfinderVolumeSecurities = new ArrayList<>();

        // creates volumes
        for (Node elfinderConfigurationVolume : elfinderConfigurationVolumes) {

            final String alias = elfinderConfigurationVolume.getAlias();
            String path = "";
            if("home".equals(flag)) {
                path = elfinderConfigurationVolume.getPath() + username;
            }else {
                path = elfinderConfigurationVolume.getPath();
            }
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            final String source = elfinderConfigurationVolume.getSource();
            final String locale = elfinderConfigurationVolume.getLocale();
            final boolean isLocked = elfinderConfigurationVolume.isLocked();
            final boolean isReadable = elfinderConfigurationVolume.isReadable();
            final boolean isWritable = elfinderConfigurationVolume.isWritable();

            // creates new volume
            Volume volume = VolumeSources.of(source).newInstance(alias, path);

            elfinderVolumes.add(volume);
            elfinderVolumeIds.put(volume, Character.toString(defaultVolumeId));
            elfinderVolumeLocales.put(volume, LocaleUtils.toLocale(locale));

            // creates security constraint
            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setLocked(isLocked);
            securityConstraint.setReadable(isReadable);
            securityConstraint.setWritable(isWritable);

            // creates volume pattern and volume security
            final String volumePattern = Character.toString(defaultVolumeId) + "_" + username + ElFinderConstants.ELFINDER_VOLUME_SERCURITY_REGEX;
            elfinderVolumeSecurities.add(new DefaultVolumeSecurity(volumePattern, securityConstraint));

            // prepare next volumeId character
            defaultVolumeId++;
        }

        defaultElfinderStorage.setThumbnailWidth(defaultThumbnailWidth);
        defaultElfinderStorage.setVolumes(elfinderVolumes);
        defaultElfinderStorage.setVolumeIds(elfinderVolumeIds);
        defaultElfinderStorage.setVolumeLocales(elfinderVolumeLocales);
        defaultElfinderStorage.setVolumeSecurities(elfinderVolumeSecurities);

        return defaultElfinderStorage;
    }
}
