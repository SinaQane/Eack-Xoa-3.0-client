package controller.back;

public class BackButtonMemory
{
    private final String page;
    private final Long tweetId;
    private final Long userId;

    public BackButtonMemory(String page)
    {
        this.page = page;
        this.tweetId = -1L;
        this.userId = -1L;
    }

    public BackButtonMemory(String page, Long userId, Long tweetId)
    {
        this.page = page;
        this.tweetId = tweetId;
        this.userId = userId;
    }

    public String getPage()
    {
        return page;
    }

    public Long getTweetId()
    {
        return tweetId;
    }

    public Long getUserId()
    {
        return userId;
    }
}
