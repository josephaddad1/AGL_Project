export class Delivery {
    constructor(

        public id: string,
        public notes: string,
        public requestedById: string,
        public clientId: string,
        public addComment: string,
        public jira: string,
        public deliveredFlag: string,
        public approvedFlag: string,
        public appNeededFlag: string,
        public createdBy: string,
        public createdDate: Date,
        public updatedBy: string, 
        public updatedDate: string,
        public releaseId: string,
        public deliveredById: string,
        public typeById: string) { }
}

