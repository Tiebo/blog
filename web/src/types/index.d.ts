export interface tag {
    id: string,
    tagName: string,
    tagViewCounts: number,
}

export interface articleData {
    username: string,
    categories: string,
    tags: string,
    article: article
}

export interface article {
    id: string,
    authorId: string,
    categoriesId: string,
    commentCounts: number,
    createDate: string,
    description: string,
    modifyDate: string,
    tagsId: string,
    title: string,
    viewCounts: number,
    weight: number,
}