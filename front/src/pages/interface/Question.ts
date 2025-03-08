export interface Question {
    id: number;
    text: string;
    options: string[];
    correctIndex: number;
    category: string;     
    difficulty: string;
}
