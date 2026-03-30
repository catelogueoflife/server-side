package com.touchkiss.catalogueoflife.spider.wikipedia.util;

import lombok.extern.slf4j.Slf4j;
import org.example.TextConverter;
import org.joda.time.DateTime;
import org.sweble.wikitext.engine.EngineException;
import org.sweble.wikitext.engine.PageId;
import org.sweble.wikitext.engine.PageTitle;
import org.sweble.wikitext.engine.WtEngineImpl;
import org.sweble.wikitext.engine.config.WikiConfig;
import org.sweble.wikitext.engine.nodes.EngProcessedPage;
import org.sweble.wikitext.engine.utils.DefaultConfigEnWp;
import org.sweble.wikitext.parser.parser.LinkTargetException;
import org.sweble.wom3.Wom3Document;
import org.sweble.wom3.swcadapter.AstToWomConverter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created on 2020/04/02 15:46
 *
 * @author Touchkiss
 */
@Slf4j
public class Parser {
    private static final Pattern TAG_PATTERN = Pattern.compile("([*\\[]+)");
    private static final Pattern SITE_IN_LINK_PATTERN = Pattern.compile("(?<=[\\{]{2})[a-zA-Z0-9_]+(?=[\\}]{2})");
    private static final Set<Character> TAG_START_SET = new HashSet<Character>() {{
        add('=');
        add('[');
        add('{');
        add('#');
        add('\'');
    }};
    private static final Set<Character> TAG_END_SET = new HashSet<Character>() {{
        add('=');
        add(']');
        add('}');
        add('\'');
    }};
    private static final Set<String> TAG_TYPE_SET = new HashSet<String>() {{
        add("image");
        add("int");
        add("aut");
        add("vn");
        add("ssp");
        add("mswsp");
        add("a");
        add("ssplast");
    }};
    private static final Set<Object> SEPARATOR_SET = new HashSet<Object>() {{
        add('|');
        add(":");
    }};

//    public static void main(String[] args) {
//        String str = "{{image|Commdolph01.jpg}}\\n=={{int:Taxonavigation}}==\\n{{Cephalorhynchus}}\\nSpecies: ''[[Cephalorhynchus commersonii]]''<br>\\nSubspecies: {{ssp|C|ephalorhynchus|c|ommersonii|commersonii}} \\n{{ssplast|C|ephalorhynchus|c|ommersonii|kerguelensis}}\\n=={{int:Name}}==\\n'''''Cephalorhynchus commersonii''''' ({{a|Bernard Germain Étienne de la Ville, Comte de Lacépède|Lacépède}}, 1804)\\n\\nEtymology: ''commersonii'' for {{a|Philibert Commerson}}, who first described them in 1767 after sighting them in the Strait of Magellan.\\n\\n==={{int:Synonyms}}===\\n*''Cephalorhynchus commersoni'' {{a|José Yáñez|Yanez}}, 1948\\n*''Delphinus commersonii'' {{a|Bernard Germain Étienne de la Ville, Comte de Lacépède|Lacépède}}, 1804\\n*''Lagenorhynchus burmeisteri'' {{a|Francisco Pascasio Moreno|Moreno}}, 1892\\n*''Lagenorhynchus floweri'' {{aut|Moreno}}, 1892\\n*''Phocoena commersonii'' {{a|Lesson}}, 1827\\n\\n=={{int:References}}==\\n* {{aut|Robineau, D.}}, {{aut|Goodall, R.N.P.}}, {{aut|Pichler, F.}} & {{aut|Baker, C.S.}} 2007. Description of a new subspecies of Commerson's dolphin, ''Cephalorhynchus commersonii'' ({{aut|Lacépède}}, 1804), inhabiting the coastal waters of the Kerguelen Islands. ''Mammalia'' 71: 172–180. ISSN (Online): 1864–1547, ISSN (Print): 0025–1461 {{doi|10.1515/MAMM.2007.034}}\\n* {{MSWsp|14300037}}\\n\\n=={{int:Vernacular names}}==\\n{{VN\\n|en=Commerson’s Dolphin\\n|fi=Kirjodelfiini\\n|hu=Commerson-delfin\\n|ja=イロワケイルカ\\n|pl=delfin czarnogłowy\\n|sv=Commersons delfin, skäckig delfin\\n|tr=Alaca yunus\\n}}\\n{{commonscat}}";
//        str = str.replaceAll("\\\\n", "\n");
//        try {
//            run(str, "Cephalorhynchus", false);
//        } catch (IOException | LinkTargetException | EngineException e) {
//            e.printStackTrace();
//        }
//    }

    static void parse(String str) {
        char[] chars = str.toCharArray();
        StringBuilder needMatch = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        boolean endTag = false;
        boolean endTemp = false;
//        开始的tag
        String startTag = "";
        Stack<String> tagStack = new Stack<>();
        int lastTagStartIndex = -1;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (TAG_START_SET.contains(c) && (needMatch.length() == 0 || (needMatch.charAt(needMatch.length() - 1)) == c)) {
                needMatch.append(c);
                endTag = true;
            } else {
                //刚结束一个tag
                if (endTag) {
                    endTag = false;
                    startTag = needMatch.toString();
                    if ("=".equals(startTag)) {
                        continue;
                    }
                    tagStack.push(startTag);
                    log.info("startTag: {}", startTag);
                    needMatch.delete(0, needMatch.length());
                    //继续另一个tag，回退一个字符。多tag嵌套
                    if (TAG_START_SET.contains(c)) {
                        i--;
                        continue;
                    }
                }
                if (!SEPARATOR_SET.contains(c)) {
                    temp.append(c);
                    endTemp = true;
                } else {
                    if (endTemp) {
                        endTemp = false;
                        log.info("截取到内容: {}", temp.toString());
                        temp.delete(0, temp.length());
                    }
                }
//                if (!)
            }
        }
    }

    static String run(String wikitext, String fileTitle, boolean renderHtml) throws IOException, LinkTargetException, EngineException {
        // Set-up a simple wiki configuration
        WikiConfig config = DefaultConfigEnWp.generate();

        final int wrapCol = 80;

        // Instantiate a compiler for wiki pages
        WtEngineImpl engine = new WtEngineImpl(config);

        // Retrieve a page
        PageTitle pageTitle = PageTitle.make(config, fileTitle);

        PageId pageId = new PageId(pageTitle, -1);

        // Compile the retrieved page
        EngProcessedPage cp = engine.postprocess(pageId, wikitext, null);

        // Convert the AST to a WOM document
        Wom3Document womDoc = AstToWomConverter.convert(
                config.getParserConfig(),
                null /*pageNamespace*/,
                null /*pagePath*/,
                pageId.getTitle().getTitle(),
                "Mr. Tester",
                DateTime.parse("2012-12-07T12:15:30.000+01:00"),
                cp.getPage());


        if (renderHtml) {
            throw new UnsupportedOperationException(
                    "HTML rendering is not yet supported!");

			/*
			String ourHtml = HtmlRenderer.print(new MyRendererCallback(), config, pageTitle, cp.getPage());

			String template = IOUtils.toString(App.class.getResourceAsStream("/render-template.html"), "UTF8");

			String html = template;
			html = html.replace("{$TITLE}", StringUtils.escHtml(pageTitle.getDenormalizedFullTitle()));
			html = html.replace("{$CONTENT}", ourHtml);

			return html;
			*/
        } else {
            TextConverter p = new TextConverter(config, wrapCol);
            String go = (String) p.go(womDoc);
            log.info("parsed content: {}", go);
            return go;
        }
    }
}
