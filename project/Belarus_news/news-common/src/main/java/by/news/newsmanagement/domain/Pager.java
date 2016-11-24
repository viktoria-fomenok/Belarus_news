package by.epam.newsmanagement.domain;

/**
 * <p>
 *     Objects of this class are used to indicate the next and previous news id concerning current news
 * </p>
 */
public class Pager {
    private Long nextId;
    private Long previousId;

    public Long getNextId() {
        return nextId;
    }

    public void setNextId(Long nextId) {
        this.nextId = nextId;
    }

    public Long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }

    @Override
    public String toString() {
        return Pager.class.getName() + " [nextId=" + nextId + ", previousId=" + previousId + "]";
    }

}
