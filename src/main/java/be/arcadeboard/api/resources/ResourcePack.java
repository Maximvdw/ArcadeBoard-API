package be.arcadeboard.api.resources;

import be.arcadeboard.api.game.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Resource pack
 * <p>
 * This resource pack contains the icons and version information information
 * it is later merged into one resource pack
 */
public class ResourcePack {
    private String name = "";
    private final Map<String, BufferedImage> iconHexMap = new HashMap<String, BufferedImage>();
    private Map<String, ResourceSound> soundNameMap = new HashMap<String, ResourceSound>();

    private final Map<String, ResourceIcon> iconNameMap = new HashMap<String, ResourceIcon>();
    private final Map<String, ResourceImage> imageNameMap = new HashMap<String, ResourceImage>();
    private Map<String, ResourceFont> fontNameMap = new HashMap<String, ResourceFont>();

    private final Map<String, Boolean> pages = new TreeMap<String, Boolean>();
    private int currentCharacterIndex = 37120;
    private int version = 1;

    public ResourcePack() {

    }

    public ResourcePack(String name, int version) {
        setName(name);
        setVersion(version);
    }

    public ResourcePack(Game game, int resourcePackVersion) {
        setVersion(resourcePackVersion);
        setName(game.getName());
    }


    /**
     * Add a resource font
     *
     * @param resourceFont resource font
     * @return resource font
     * @throws IOException
     */
    public ResourceFont addFont(ResourceFont resourceFont) throws IOException {
        fontNameMap.put(resourceFont.getName(), resourceFont);
        for (ResourceIcon icon : resourceFont.getCharacters().values()) {
            addIcon(icon.getName(), icon, icon.isAllowRotation());
        }
        return resourceFont;
    }

    /**
     * Add a resource font
     *
     * @param name     name of the font
     * @param font     InputStream of the TTF file
     * @param fontSize font size
     * @return resource font
     * @throws IOException
     * @throws FontFormatException
     */
    public ResourceFont addFont(String name, InputStream font, float fontSize) throws IOException, FontFormatException {
        ResourceFont resourceFont = new ResourceFont(name, font, fontSize);
        fontNameMap.put(resourceFont.getName(), resourceFont);
        for (ResourceIcon icon : resourceFont.getCharacters().values()) {
            addIcon(icon.getName(), icon, icon.isAllowRotation());
        }
        return resourceFont;
    }

    /**
     * Add a resource font
     *
     * @param name name of the font
     * @param font AWT font
     * @return
     * @throws IOException
     */
    public ResourceFont addFont(String name, Font font) throws IOException {
        ResourceFont resourceFont = new ResourceFont(name, font);
        fontNameMap.put(resourceFont.getName(), resourceFont);
        for (ResourceIcon icon : resourceFont.getCharacters().values()) {
            addIcon(icon.getName(), icon, icon.isAllowRotation());
        }
        return resourceFont;
    }

    /**
     * Add a new image
     *
     * @param name  Name of the image
     * @param image input stream
     * @return ResourceIcon
     */
    public ResourceImage addImage(String name, InputStream image) throws IOException {
        return addImage(name, new ResourceImage(name, image), false);
    }

    /**
     * Add a resource image
     *
     * @param resourceImage existing image
     * @return new resource icon
     * @throws IOException
     */
    public ResourceImage addImage(ResourceImage resourceImage) throws IOException {
        return addImage(resourceImage.getName(), resourceImage.getImage());
    }

    /**
     * Add a new image
     *
     * @param name  Name of the image
     * @param image BufferedImage
     * @return ResourceIcon
     * @throws IOException
     */
    public ResourceImage addImage(String name, BufferedImage image) throws IOException {
        return addImage(name, new ResourceImage(name, image), false);
    }

    /**
     * Add a new image
     *
     * @param name  Name of the image
     * @param image Image file
     * @return ResourceIcon
     */
    public ResourceImage addImage(String name, File image) throws IOException {
        return addImage(name, new ResourceImage(name, image), false);
    }

    /**
     * Add a new image
     *
     * @param name  Name of the image
     * @param image input stream
     * @return ResourceIcon
     */
    public ResourceImage addImage(String name, InputStream image, boolean allowRotation) throws IOException {
        return addImage(name, new ResourceImage(name, image), allowRotation);
    }

    /**
     * Add a new image
     *
     * @param name          Name of the image
     * @param image         Image file
     * @param allowRotation Allow rotation
     * @return ResourceIcon
     */
    public ResourceImage addImage(String name, File image, boolean allowRotation) throws IOException {
        return addImage(name, new ResourceImage(name, image), allowRotation);
    }

    /**
     * Add a new image
     *
     * @param name          Name of the image
     * @param image         Text icon
     * @param allowRotation Allow rotation
     * @return ResourceIcon
     */
    public ResourceImage addImage(String name, ResourceImage image, boolean allowRotation) throws IOException {
        for (int i = 0; i < image.getIcons().length; i++) {
            for (int j = 0; j < image.getIcons()[i].length; j++) {
                ResourceIcon icon = image.getIcons()[i][j];
                icon.setAllowRotation(allowRotation);
                icon.setHex(Integer.toHexString(currentCharacterIndex++));
                iconNameMap.put(icon.getName().toUpperCase(), icon);
                pages.put(icon.getHex().substring(0, 2), true);
                iconHexMap.put(icon.getHex(), icon.getImage());
                if (allowRotation) {
                    iconHexMap.put(icon.getHex(1), icon.getImage(1));
                    iconHexMap.put(icon.getHex(2), icon.getImage(2));
                    iconHexMap.put(icon.getHex(3), icon.getImage(3));
                }
            }
        }
        imageNameMap.put(name.toUpperCase(), image);
        return image;
    }


    /**
     * Add a new text icon
     *
     * @param name  Name of the icon
     * @param image input stream
     * @return ResourceIcon
     */
    public ResourceIcon addIcon(String name, InputStream image) throws IOException {
        return addIcon(name, new ResourceIcon(name, image), false);
    }

    /**
     * Add a resource icon
     *
     * @param resourceIcon existing icon
     * @return new resource icon
     * @throws IOException
     */
    public ResourceIcon addIcon(ResourceIcon resourceIcon) throws IOException {
        return addIcon(resourceIcon.getName(), resourceIcon.getImage());
    }

    /**
     * Add a new text icon
     *
     * @param name  Name of the icon
     * @param image BufferedImage
     * @return ResourceIcon
     * @throws IOException
     */
    public ResourceIcon addIcon(String name, BufferedImage image) throws IOException {
        return addIcon(name, new ResourceIcon(name, image), false);
    }

    /**
     * Add a new text icon
     *
     * @param name  Name of the icon
     * @param image Image file
     * @return ResourceIcon
     */
    public ResourceIcon addIcon(String name, File image) throws IOException {
        return addIcon(name, new ResourceIcon(name, image), false);
    }

    /**
     * Add a new text icon
     *
     * @param name  Name of the icon
     * @param image input stream
     * @return ResourceIcon
     */
    public ResourceIcon addIcon(String name, InputStream image, boolean allowRotation) throws IOException {
        return addIcon(name, new ResourceIcon(name, image), allowRotation);
    }

    /**
     * Add a new text icon
     *
     * @param name          Name of the icon
     * @param image         Image file
     * @param allowRotation Allow rotation
     * @return ResourceIcon
     */
    public ResourceIcon addIcon(String name, File image, boolean allowRotation) throws IOException {
        return addIcon(name, new ResourceIcon(name, image), allowRotation);
    }

    /**
     * Add a new text icon
     *
     * @param name          Name of the icon
     * @param icon          Text icon
     * @param allowRotation Allow rotation
     * @return ResourceIcon
     */
    public ResourceIcon addIcon(String name, ResourceIcon icon, boolean allowRotation) throws IOException {
        icon.setAllowRotation(allowRotation);
        icon.setHex(Integer.toHexString(currentCharacterIndex++));
        iconNameMap.put(name.toUpperCase(), icon);
        pages.put(icon.getHex().substring(0, 2), true);
        iconHexMap.put(icon.getHex(), icon.getImage());
        if (allowRotation) {
            iconHexMap.put(icon.getHex(1), icon.getImage(1));
            iconHexMap.put(icon.getHex(2), icon.getImage(2));
            iconHexMap.put(icon.getHex(3), icon.getImage(3));
            currentCharacterIndex += 3;
        }
        return icon;
    }

    /**
     * Add a new sound
     *
     * @param name  Name of the sound
     * @param sound Sound
     * @return resource sound
     */
    public ResourceSound addSound(String name, ResourceSound sound) {
        sound.setName(name);
        soundNameMap.put(name, sound);
        return sound;
    }

    /**
     * Add a new sound
     *
     * @param name        Name of the sound
     * @param soundStream Sound stream
     * @return resource sound
     */
    public ResourceSound addSound(String name, InputStream soundStream) {
        ResourceSound sound = new ResourceSound(name, soundStream);
        soundNameMap.put(name, sound);
        return sound;
    }


    /**
     * Set text icon
     *
     * @param hex  Replace character at hex pos
     * @param icon Text icon
     * @return saved icon
     */
    public ResourceIcon setIcon(String hex, ResourceIcon icon) {
        icon.setHex(hex);
        iconHexMap.put(hex, icon.getImage());
        iconNameMap.put(icon.getName().toUpperCase(), icon);
        pages.put(hex.substring(0, 2), true);
        return icon;
    }

    /**
     * Get font by name
     *
     * @param name name of the font
     * @return Font if found
     */
    public ResourceFont getFontByName(String name) {
        if (fontNameMap.containsKey(name.toUpperCase())) {
            return fontNameMap.get(name.toUpperCase());
        } else {
            return null;
        }
    }

    /**
     * Get sound by name
     *
     * @param name name of the icon
     * @return Sound if found
     */
    public ResourceSound getSoundByName(String name) {
        return soundNameMap.getOrDefault(name, null);
    }

    /**
     * Get icon by name
     *
     * @param name name of the icon
     * @return Icon if found
     */
    public ResourceIcon getIconByName(String name) {
        return iconNameMap.getOrDefault(name.toUpperCase(), null);
    }

    /**
     * Get image by name
     *
     * @param name name of the image
     * @return Image if found
     */
    public ResourceImage getImageByName(String name) {
        return imageNameMap.getOrDefault(name.toUpperCase(), null);
    }


    /**
     * Merge the current resource pack with another
     *
     * @param resourcePack resource pack to merge
     * @throws IOException
     */
    public void merge(ResourcePack resourcePack) throws IOException {
        // Merge icons
        for (Map.Entry<String, ResourceIcon> icon : resourcePack.getIconNameMap().entrySet()) {
            addIcon(icon.getKey(), icon.getValue(), icon.getValue().isAllowRotation());
        }
        // Merge images
        imageNameMap.putAll(resourcePack.getImageNameMap());
        // Merge fonts
        fontNameMap.putAll(resourcePack.getFontNameMap());
        // Merge sounds
        soundNameMap.putAll(resourcePack.getSoundNameMap());
        // Increment version
        setVersion(getVersion() + resourcePack.getVersion());
    }

    /**
     * Get version
     *
     * @return version
     */
    public int getVersion() {
        return version;
    }

    /**
     * Set version
     *
     * @param version Version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Get icon hex map
     *
     * @return map with key being hex
     */
    public Map<String, BufferedImage> getIconHexMap() {
        return iconHexMap;
    }

    /**
     * Get icon name map
     *
     * @return map with key being name
     */
    public Map<String, ResourceIcon> getIconNameMap() {
        return iconNameMap;
    }

    /**
     * Get image name map
     *
     * @return map with key being name
     */
    public Map<String, ResourceImage> getImageNameMap() {
        return imageNameMap;
    }

    /**
     * Get used pages
     *
     * @return used pages
     */
    public Map<String, Boolean> getUsedPages() {
        return pages;
    }

    public Map<String, ResourceSound> getSoundNameMap() {
        return soundNameMap;
    }

    public void setSoundNameMap(Map<String, ResourceSound> soundNameMap) {
        this.soundNameMap = soundNameMap;
    }

    public Map<String, ResourceFont> getFontNameMap() {
        return fontNameMap;
    }

    public void setFontNameMap(Map<String, ResourceFont> fontNameMap) {
        this.fontNameMap = fontNameMap;
    }

    /**
     * Get resource pack name
     *
     * @return resource pack name
     */
    public String getName() {
        return name;
    }

    /**
     * Set resource pack name
     *
     * @param name resource pack name
     */
    public void setName(String name) {
        this.name = name;
    }
}
