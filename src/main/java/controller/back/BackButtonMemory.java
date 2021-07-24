package controller.back;

public class BackButtonMemory
{
    private final String page;
    private final String tweetId;
    private final Long userId;

    public BackButtonMemory(String page)
    {
        this.page = page;
        this.tweetId = "";
        this.userId = 0L;
    }

    public BackButtonMemory(String page, String tweetId)
    {
        this.page = page;
        this.tweetId = tweetId;
        this.userId = 0L;
    }

    public BackButtonMemory(String page, Long userId)
    {
        this.page = page;
        this.tweetId = "";
        this.userId = userId;
    }

    public String getPage()
    {
        return page;
    }

    public String getTweetId()
    {
        return tweetId;
    }

    public Long getUserId()
    {
        return userId;
    }
}
