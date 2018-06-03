package project;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.StringLayout;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.ArrayList;

/**
 * Created by Антон on 03.06.2018.
 */
@Plugin(name = "TextAreaAppender", category = "Core", elementType = "appender", printObject = true)
public class TextAreaAppender extends AbstractWriterAppender {

    private static volatile ArrayList<TextArea> textAreaList = new ArrayList<TextArea>();

    private int maxLines = 0;
    private int currentLinesNumber = 0;

    protected TextAreaAppender(String name, StringLayout layout, Filter filter, int maxLines, boolean ignoreExceptions, WriterManager manager) {

        super(name, layout, filter, ignoreExceptions, true, manager);
        this.maxLines = maxLines;
    }

    @PluginFactory
    public static TextAreaAppender createAppender(@PluginAttribute("name") String name,
                                                   @PluginAttribute("maxLines") int maxLines,
                                                   @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                                   @PluginElement("Layout") StringLayout layout,
                                                   @PluginElement("Filters") Filter filter,
                                                    @PluginElement("Filters") WriterManager manager) {

        if (name == null) {
            LOGGER.error("No name provided for JTextAreaAppender");
            return null;
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TextAreaAppender(name, layout, filter, maxLines, ignoreExceptions, manager);
    }


    public static void addTextArea(final TextArea textArea) {
        textAreaList.add(textArea);
    }

    @Override
    public void append(final LogEvent event) {

        final String message = new String(this.getLayout().toByteArray(event));


        try {
            Platform.runLater(() -> {
                for (TextArea ta : textAreaList){
                    if(ta !=null)
                    {
                        currentLinesNumber++;
                        if(maxLines > 0 && currentLinesNumber > maxLines)
                        {
                            String text = ta.getText();

                            ta.replaceText(0, text.indexOf("\n", 0) + 1, "");
                            ta.positionCaret(ta.getText().length());
                            currentLinesNumber--;
                        }
                        ta.appendText(message);
                    }
                }

            });
        } catch (IllegalStateException e) {

        }
    }
}