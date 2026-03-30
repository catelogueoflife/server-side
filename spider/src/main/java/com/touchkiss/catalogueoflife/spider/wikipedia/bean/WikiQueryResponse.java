package com.touchkiss.catalogueoflife.spider.wikipedia.bean;

import java.util.List;

/**
 * Created on 2020/04/02 17:13
 *
 * @author Touchkiss
 */
public class WikiQueryResponse {
    private boolean batchcomplete;
    private WarningsBean warnings;
    private QueryBean query;

    public boolean isBatchcomplete() {
        return batchcomplete;
    }

    public void setBatchcomplete(boolean batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    public WarningsBean getWarnings() {
        return warnings;
    }

    public void setWarnings(WarningsBean warnings) {
        this.warnings = warnings;
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public static class WarningsBean {
        private MainBean main;
        private RevisionsBean revisions;

        public MainBean getMain() {
            return main;
        }

        public void setMain(MainBean main) {
            this.main = main;
        }

        public RevisionsBean getRevisions() {
            return revisions;
        }

        public void setRevisions(RevisionsBean revisions) {
            this.revisions = revisions;
        }

        public static class MainBean {
            private String warnings;

            public String getWarnings() {
                return warnings;
            }

            public void setWarnings(String warnings) {
                this.warnings = warnings;
            }
        }

        public static class RevisionsBean {
            private String warnings;

            public String getWarnings() {
                return warnings;
            }

            public void setWarnings(String warnings) {
                this.warnings = warnings;
            }
        }
    }

    public static class QueryBean {
        private List<NormalizedBean> normalized;
        private List<PagesBean> pages;

        public List<NormalizedBean> getNormalized() {
            return normalized;
        }

        public void setNormalized(List<NormalizedBean> normalized) {
            this.normalized = normalized;
        }

        public List<PagesBean> getPages() {
            return pages;
        }

        public void setPages(List<PagesBean> pages) {
            this.pages = pages;
        }

        public static class NormalizedBean {
            private boolean fromencoded;
            private String from;
            private String to;

            public boolean isFromencoded() {
                return fromencoded;
            }

            public void setFromencoded(boolean fromencoded) {
                this.fromencoded = fromencoded;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }
        }

        public static class PagesBean {
            private int pageid;
            private int ns;
            private String title;
            private List<RevisionsBeanX> revisions;

            public int getPageid() {
                return pageid;
            }

            public void setPageid(int pageid) {
                this.pageid = pageid;
            }

            public int getNs() {
                return ns;
            }

            public void setNs(int ns) {
                this.ns = ns;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<RevisionsBeanX> getRevisions() {
                return revisions;
            }

            public void setRevisions(List<RevisionsBeanX> revisions) {
                this.revisions = revisions;
            }

            public static class RevisionsBeanX {
                private String contentformat;
                private String contentmodel;
                private String content;

                public String getContentformat() {
                    return contentformat;
                }

                public void setContentformat(String contentformat) {
                    this.contentformat = contentformat;
                }

                public String getContentmodel() {
                    return contentmodel;
                }

                public void setContentmodel(String contentmodel) {
                    this.contentmodel = contentmodel;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }
        }
    }
}
